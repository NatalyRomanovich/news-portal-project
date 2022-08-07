package by.htp.jd2.dao.connectionpool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.logging.Logger;
import by.htp.jd2.dao.connectionpool.DBParameter;
import by.htp.jd2.dao.connectionpool.DBResourseManager;

public final class ConnectionPool {
	
	private static final int QUEUES_NUMBER = 2;
	public static List <String> errorsMessagesConPool = new ArrayList<>();
	private BlockingQueue <Connection> connectionQueue;
	private BlockingQueue <Connection> givenAwayConQueue;
	
	private String driverName;
	private String url;
	private String user;
	private String password;
	private int poolsize;	
	
	private static ConnectionPool connectionPool;		
	//private static volatile boolean stopQueueProcessing = false;

	private ConnectionPool() throws ConnectionPoolException {
		
		DBResourseManager dbResourseManager = DBResourseManager.getInstance();
		this.driverName = dbResourseManager.getValue(DBParameter.DB_DRIVER);
		this.url = dbResourseManager.getValue(DBParameter.DB_URL);
		this.user = dbResourseManager.getValue(DBParameter.DB_USER);
		this.password = dbResourseManager.getValue(DBParameter.DB_PASSWORD);
		
		try {
			this.poolsize = Integer.parseInt (dbResourseManager.getValue(DBParameter.DB_POOL_SIZE));
		}catch (NumberFormatException e) {
			poolsize = 5;
		}	
		
		initPoolData();
	}
	
	public static ConnectionPool getInstance() throws ConnectionPoolException {
    	if(connectionPool == null) {
    		connectionPool = new ConnectionPool(); 		    		
    	}
    	
        return connectionPool;
    }
	
	public void initPoolData () throws ConnectionPoolException{
				
		try {
			Class.forName(driverName);
						
			givenAwayConQueue = new ArrayBlockingQueue (poolsize);
			connectionQueue = new ArrayBlockingQueue (poolsize);
			
			for (int i = 0; i < poolsize; i++) {
				Connection connection = DriverManager.getConnection(url, user, password);				
				PooledConnection pooledConnection = new PooledConnection (connection);
				connectionQueue.add(pooledConnection);
				
			}
				
		}catch (SQLException e) {
			throw new ConnectionPoolException ("SQLException in connection pool");
		}
		catch (ClassNotFoundException e) {
			throw new ConnectionPoolException ("Can't find database driver class", e);
		}
	}
		 			
	 public void clearConnectionQueue() {
		 //stopQueueProcessing = true;
		
		BlockingQueue  <Connection> queueToRemoveConnections= new ArrayBlockingQueue (QUEUES_NUMBER);
		moveElementsFromQueue (connectionQueue, queueToRemoveConnections);
		moveElementsFromQueue (givenAwayConQueue, queueToRemoveConnections);
		
		try { 
			//if (connectionQueue.peek() != null) {
			//if (connectionQueue.remainingCapacity() == 0) {
			closeConnectionsQueue (queueToRemoveConnections);
			//closeConnectionsQueue (givenAwayConQueue);
			//closeConnectionsQueue (connectionQueue);
			//}
			//else {
				
			//}
		} catch (SQLException e) {
			//Logger.log (Level.ERROR, "Error closing the connection", e);
		}
	}
	
	 public void moveElementsFromQueue (BlockingQueue <Connection> queue, BlockingQueue <Connection> queueToRemoveConnections) {

			 while (queue.peek() != null) {
				 queueToRemoveConnections.add(queue.poll());				 					 
		 }		
	 }
	 
	public Connection takeConnection () throws ConnectionPoolException {
		Connection connection = null;		
		
		try {
			//if (!stopQueueProcessing){
				connection = connectionQueue.take();
				givenAwayConQueue.add(connection);
			//}
			/*else {
				throw new NullPointerException ("Database connection not possible");
			};*/
			
		 }catch (InterruptedException e) {
			/*String message = "Error connecting to the data source";
			 getErrorsListMessage (message);		*/
		 }
		
		return connection;
	}
	
	public void closeConnection (Connection con, Statement st, ResultSet rs) throws ConnectionPoolException {
		
		try {
			con.close();			
		}catch (SQLException e) {
			//Logger.log (Level.ERROR, "Connection is not return to the pool");			
		}
		
		try {
			rs.close();			
		}catch (SQLException e) {
			//Logger.log (Level.ERROR, "ResultSet is not closed");			
		}
		
		try {
			st.close();			
		}catch (SQLException e) {
			//Logger.log (Level.ERROR, "Statement is not closed");			
		}
	
	}
	
    public void closeConnection (Connection con, Statement st) throws ConnectionPoolException {
		
		try {
			con.close();			
		}catch (SQLException e) {
			//Logger.log (Level.ERROR, "Connection is not return to the pool");			
		}			
		
		try {
			st.close();			
		}catch (SQLException e) {
			//Logger.log (Level.ERROR, "Statement is not closed");			
		}
	
	}
    
    public void closeConnectionsQueue (BlockingQueue <Connection> queue) throws SQLException {
    	Connection connection;
    	while ((connection = queue.poll()) != null) {
    		if (!connection.getAutoCommit()) {
    			connection.commit();
    		}
    		((PooledConnection) connection).reallyClose ();
    	}
    }
    
    public List <String> getErrorsListMessage (String message){
		errorsMessagesConPool.add(message);
		
		return errorsMessagesConPool;
	}

	private class PooledConnection implements Connection {
		private Connection connection;
		
		public PooledConnection (Connection c) throws SQLException {
			this.connection =c;
			this.connection.setAutoCommit(true);
		}
		
		public void reallyClose () throws SQLException {
			connection.close();
		}
		
		@Override
		public void clearWarnings () throws SQLException {
			connection.clearWarnings();
		}
		
		@Override
		public void close () throws SQLException {
			if (connection.isClosed()) {
				throw new SQLException ("Attampting to close closed connection");				
			}
			
			if (connection.isReadOnly()) {
				connection.setReadOnly(false);
			}
			
			if (!givenAwayConQueue.remove (this)) {
				throw new SQLException ("Error deleting connection from the given away connection pool");	
			}
			
			if (!connectionQueue.offer (this)) {
				throw new SQLException ("Error allocating connection in the pool");	
			}
		}
		
		@Override
		public void commit () throws SQLException {
			connection.commit();
		}
		
		@Override
		public Array createArrayOf (String typeName, Object [] elements)  throws SQLException {
			return connection.createArrayOf(typeName, elements);
		}
		
		@Override
		public Blob createBlob ()  throws SQLException {
			return connection.createBlob();			
		}
		
		@Override
		public Clob createClob ()  throws SQLException {
			return connection.createClob();			
		}
		
		@Override
		public NClob createNClob ()  throws SQLException {
			return connection.createNClob();			
		}
		
		@Override
		public SQLXML createSQLXML()  throws SQLException {
			return connection.createSQLXML();			
		}
		
		@Override
		public Statement createStatement ()  throws SQLException {					
			return connection.createStatement();			
		}
		
		@Override
		public Statement createStatement (int resultSetType,int resultSetConcurrency)  throws SQLException {					
			return connection.createStatement(resultSetType,resultSetConcurrency);			
		}
		
		@Override
		public Statement createStatement (int resultSetType,int resultSetConcurrency, int resultSetHoldability)  throws SQLException {					
			return connection.createStatement(resultSetType,resultSetConcurrency, resultSetHoldability);			
		}
		
		@Override
		public Struct createStruct (String typeName, Object [] attributs)  throws SQLException {
			return connection.createStruct(typeName, attributs);
		}
		
		@Override
		public boolean getAutoCommit()  throws SQLException {
			return connection.getAutoCommit();			
		}
		
		@Override
		public String getCatalog()  throws SQLException {
			return connection.getCatalog();			
		}
		
		@Override
		public Properties getClientInfo()  throws SQLException {
			return connection.getClientInfo();			
		}
		
		@Override
		public String getClientInfo(String name)  throws SQLException {
			return connection.getClientInfo(name);			
		}
		
		@Override
		public int getHoldability ()  throws SQLException {
			return connection.getHoldability();			
		}
		
		@Override
		public DatabaseMetaData getMetaData ()  throws SQLException {
			return connection.getMetaData();			
		}
		
		@Override
		public int getTransactionIsolation ()  throws SQLException {
			return connection.getTransactionIsolation();			
		}
		
		@Override
		public Map <String,Class <?>> getTypeMap ()  throws SQLException {
			return connection.getTypeMap();			
		}
		
		@Override
		public SQLWarning getWarnings ()  throws SQLException {
			return connection.getWarnings();			
		}
		
		@Override
		public boolean isClosed ()  throws SQLException {
			return connection.isClosed();			
		}
		
		@Override
		public boolean isReadOnly ()  throws SQLException {
			return connection.isReadOnly();			
		}
		
		@Override
		public boolean isValid (int timeout)  throws SQLException {
			return connection.isValid (timeout);			
		}
		
		@Override
		public String nativeSQL(String sql)  throws SQLException {
			return connection.nativeSQL(sql);			
		}
		
		@Override
		public CallableStatement prepareCall(String sql)  throws SQLException {
			return connection.prepareCall(sql);			
		}
		
		@Override
		public CallableStatement prepareCall(String sql, int resultSetType,int resultSetConcurrency)  throws SQLException {
			return connection.prepareCall(sql,resultSetType,resultSetConcurrency );			
		}
		
		@Override
		public CallableStatement prepareCall(String sql, int resultSetType,int resultSetConcurrency, int resultSetHoldability)  throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability );			
		}
		
		@Override
		public PreparedStatement prepareStatement(String sql)  throws SQLException {
			return connection.prepareStatement(sql);			
		}
		
		@Override
		public PreparedStatement prepareStatement(String sql, int auroGenerateKeys)  throws SQLException {
			return connection.prepareStatement(sql, auroGenerateKeys );			
		}
		
		@Override
		public PreparedStatement prepareStatement(String sql, int [] columnIndexes)   throws SQLException {
			return connection.prepareStatement(sql, columnIndexes);			
		}
		
		@Override
		public PreparedStatement prepareStatement(String sql, String [] columnNames)  throws SQLException {
			return connection.prepareStatement(sql, columnNames);			
		}
		
		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType,int resultSetConcurrency)  throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);			
		}
		
		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType,int resultSetConcurrency, int resultSetHoldability)  throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);			
		}
		
		@Override
		public void rollback ()  throws SQLException {
			connection.rollback();			
		}
		
		@Override
		public void setAutoCommit (boolean autoCommit)  throws SQLException {
			connection.setAutoCommit(autoCommit);			
		}
		
		@Override
		public void setCatalog (String catalog)  throws SQLException {
			connection.setCatalog (catalog);			
		}
		
		@Override
		public void setClientInfo (String name, String value)  throws SQLClientInfoException {
			connection.setClientInfo (name, value);			
		}
		
		@Override
		public void setHoldability (int holdability)  throws SQLException {
			connection.setHoldability (holdability);			
		}
		
		@Override
		public void setReadOnly (boolean readOnly)  throws SQLException {
			connection.setReadOnly (readOnly);			
		}
		
		@Override
		public Savepoint setSavepoint ()  throws SQLException {
			return connection.setSavepoint ();			
		}
		
		@Override
		public Savepoint setSavepoint (String name)  throws SQLException {
			return connection.setSavepoint (name);			
		}
		
		@Override
		public void setTransactionIsolation (int level)  throws SQLException {
			connection.setTransactionIsolation (level);			
		}
		
		@Override
		public boolean isWrapperFor (Class <?> iface)  throws SQLException {
			return connection.isWrapperFor  (iface);			
		}
		
		@Override
		public <T> T unwrap (Class <T> iface)  throws SQLException {
			return connection.unwrap  (iface);			
		}
		
		@Override
		public void abort (Executor arg0)  throws SQLException {
			connection.abort (arg0);			
		}
		
		@Override
		public int getNetworkTimeout ()  throws SQLException {
			return connection.getNetworkTimeout  ();			
		}
		
		@Override
		public String getSchema ()  throws SQLException {
			return connection.getSchema();			
		}
		
		@Override
		public void releaseSavepoint (Savepoint arg0)  throws SQLException {
			connection.releaseSavepoint (arg0);			
		}
		
		@Override
		public void rollback (Savepoint arg0)  throws SQLException {
			connection.rollback (arg0);			
		}
		
		@Override
		public void setClientInfo (Properties arg0)  throws SQLClientInfoException {
			connection.setClientInfo (arg0);			
		}
		
		@Override
		public void setNetworkTimeout (Executor arg0, int arg1)  throws SQLException {
			connection.setNetworkTimeout  (arg0, arg1);			
		}
		
		@Override
		public void setSchema (String arg0)  throws SQLException {
			connection.setSchema  (arg0);			
		}
		
		@Override
		public void setTypeMap (Map <String, Class <?>> arg0)  throws SQLException {
			connection.setTypeMap (arg0);			
		}	
				
	}
	
}
