package interbase.interclient;

import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public final class DatabaseMetaData implements java.sql.DatabaseMetaData {
   private interbase.interclient.Connection connection_;
   private interbase.interclient.PreparedStatement pstmt_;
   private interbase.interclient.ResultSet rs_;
   String userName_ = null;
   String databaseProductVersion_ = null;
   int ibMajorVersion_;
   int ibMinorVersion_ = -1;
   int odsMajorVersion_;
   int odsMinorVersion_;
   int pageSize_;
   int pageAllocation_;
   int databaseSQLDialect_;
   boolean databaseReadOnly_;
   int attachmentSQLDialect_;
   private static final String TABLE = "TABLE";
   private static final String SYSTEM_TABLE = "SYSTEM TABLE";
   private static final String VIEW = "VIEW";
   private static final String SPACES = "                               ";
   private static final String UNION = "UNION ";
   public static final String[] ALL_TYPES = new String[]{"SYSTEM TABLE", "TABLE", "VIEW"};
   private static final String GET_ALL_TABLES_SYSTEM = "select  cast(RDB$RELATION_NAME as VARCHAR(31)) as TABLE_NAME, cast('SYSTEM TABLE' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$SYSTEM_FLAG = 1 and RDB$VIEW_SOURCE is null ";
   private static final String GET_ALL_TABLES_TABLE = " select  cast(RDB$RELATION_NAME as VARCHAR(31)) as TABLE_NAME, cast('TABLE' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$SYSTEM_FLAG = 0 and RDB$VIEW_SOURCE is null ";
   private static final String GET_ALL_TABLES_VIEW = " select  cast(RDB$RELATION_NAME as VARCHAR(31)) as TABLE_NAME, cast('VIEW' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$VIEW_SOURCE is not null";
   private static final String GET_EXACT_TABLES_SYSTEM = "select  cast(RDB$RELATION_NAME as VARCHAR(31)) as TABLE_NAME, cast('SYSTEM TABLE' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where  RDB$SYSTEM_FLAG = 1 and RDB$VIEW_SOURCE is null and ? = RDB$RELATION_NAME ";
   private static final String GET_EXACT_TABLES_TABLE = " select  cast(RDB$RELATION_NAME as varchar(31)) as TABLE_NAME, cast('TABLE' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$SYSTEM_FLAG = 0 and RDB$VIEW_SOURCE is null and ? = RDB$RELATION_NAME ";
   private static final String GET_EXACT_TABLES_VIEW = " select  cast(RDB$RELATION_NAME as varchar(31)) as TABLE_NAME, cast('VIEW' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$VIEW_SOURCE is not null and ? = RDB$RELATION_NAME";
   private static final String GET_WILD_TABLES_SYSTEM = "select  cast(RDB$RELATION_NAME as VARCHAR(31)) as TABLE_NAME, cast('SYSTEM TABLE' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$SYSTEM_FLAG = 1 and RDB$VIEW_SOURCE is null and RDB$RELATION_NAME || '                               ' like ? escape '\\' ";
   private static final String GET_WILD_TABLES_TABLE = " select  cast(RDB$RELATION_NAME as VARCHAR(31)) as TABLE_NAME, cast('TABLE' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$SYSTEM_FLAG = 0 and RDB$VIEW_SOURCE is null and RDB$RELATION_NAME || '                               ' like ? escape '\\'";
   private static final String GET_WILD_TABLES_VIEW = " select  cast(RDB$RELATION_NAME as VARCHAR(31)) as TABLE_NAME, cast('VIEW' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$VIEW_SOURCE is not null and RDB$RELATION_NAME || '                               ' like ? escape '\\' ";
   private static final String ALL_PROCS_CALLABLE = "select P.RDB$PROCEDURE_NAME from RDB$PROCEDURES P where not exists ( select UP.RDB$RELATION_NAME from RDB$USER_PRIVILEGES UP where (UP.RDB$USER = ? or UP.RDB$USER = 'PUBLIC') and UP.RDB$RELATION_NAME = P.RDB$PROCEDURE_NAME and UP.RDB$OBJECT_TYPE = 5 and UP.RDB$PRIVILEGE = 'X' )";
   private static final String ALL_TABLES_SELECTABLE = "select REL.RDB$RELATION_NAME from RDB$RELATIONS REL where (REL.RDB$SYSTEM_FLAG = 0) and  not exists (  select UP.RDB$RELATION_NAME from  RDB$USER_PRIVILEGES UP  where UP.RDB$OBJECT_TYPE = 0 and UP.RDB$USER_TYPE = 8 and UP.RDB$RELATION_NAME = REL.RDB$RELATION_NAME and  (RDB$USER = ? or RDB$USER = 'PUBLIC') and (RDB$PRIVILEGE IN ('S',  'A')))";
   private static final String GET_PROCEDURES_START = "select  RDB$PROCEDURE_NAME as PROCEDURE_NAME, RDB$DESCRIPTION as REMARKS, RDB$PROCEDURE_OUTPUTS as PROCEDURE_TYPE,RDB$OWNER_NAME from RDB$PROCEDURES where ";
   private static final String GET_PROCEDURES_END = "1 = 1 order by 1";
   private static final String GET_TYPE_INFO_DIALECT_1 = "select 1, -7  from RDB$DATABASE union select 1, -6  from RDB$DATABASE union select 4, -5  from RDB$DATABASE union select 12, -4 from RDB$DATABASE union select 12, -3 from RDB$DATABASE union select 12, -2 from RDB$DATABASE union select 10, -1 from RDB$DATABASE union select 8, 1   from RDB$DATABASE union select 5, 2   from RDB$DATABASE union select 5, 3   from RDB$DATABASE union select 2, 4   from RDB$DATABASE union select 1, 5   from RDB$DATABASE union select 4, 6   from RDB$DATABASE union select 3, 7   from RDB$DATABASE union select 4, 8   from RDB$DATABASE union select 9, 12  from RDB$DATABASE union select 11, 91 from RDB$DATABASE union select 11, 92 from RDB$DATABASE union select 11, 93 from RDB$DATABASE";
   private static final String GET_TYPE_INFO_DIALECT_3 = "select 1, -7  from RDB$DATABASE union select 1, -6  from RDB$DATABASE union select 17, -5 from RDB$DATABASE union select 12, -4 from RDB$DATABASE union select 12, -3 from RDB$DATABASE union select 12, -2 from RDB$DATABASE union select 10, -1 from RDB$DATABASE union select 8, 1   from RDB$DATABASE union select 17, 2  from RDB$DATABASE union select 18, 3  from RDB$DATABASE union select 2, 4   from RDB$DATABASE union select 1, 5   from RDB$DATABASE union select 4, 6   from RDB$DATABASE union select 3, 7   from RDB$DATABASE union select 4, 8   from RDB$DATABASE union select 9, 12  from RDB$DATABASE union select 15, 91 from RDB$DATABASE union select 16, 92 from RDB$DATABASE union select 11, 93 from RDB$DATABASE";
   private static final int CATALOG_ALL_PROCEDURES_ARE_CALLABLE__ = 16;
   private static final int CATALOG_ALL_TABLES_ARE_SELECTABLE__ = 17;
   private final String numericFunctions = "SIN, COS, TAN ";
   private static final String stringFunctions = "LENGTH, SUBSTRING, CONCAT, LCASE, LTRIM, CHAR, ASCII, USER, UCASE, LTRIM RTRIM ";
   public static final int procedureResultUnknown = 0;
   public static final int procedureNoResult = 1;
   public static final int procedureReturnsResult = 2;
   private static final String GET_PROCEDURE_COLUMNS_START = "select PROC.RDB$PROCEDURE_NAME as PROCEDURE_NAME, PROCPARA.RDB$PARAMETER_NAME as COLUMN_NAME, PROCPARA.RDB$PARAMETER_TYPE as COLUMN_TYPE, FIELD.RDB$FIELD_TYPE AS FIELD_TYPE,  FIELD.RDB$FIELD_SUB_TYPE AS FIELD_SUB_TYPE,  FIELD.RDB$FIELD_PRECISION as FIELD_PRECISION, FIELD.RDB$FIELD_SCALE as FIELD_SCALE, FIELD.RDB$FIELD_LENGTH as FIELD_LENGTH, FIELD.RDB$NULL_FLAG as NULLABLE, PROCPARA.RDB$DESCRIPTION as REMARKS from RDB$PROCEDURES PROC, RDB$PROCEDURE_PARAMETERS PROCPARA, RDB$FIELDS FIELD where ";
   private static final String GET_PROCEDURE_COLUMNS_END = " PROC.RDB$PROCEDURE_NAME = PROCPARA.RDB$PROCEDURE_NAME and PROCPARA.RDB$FIELD_SOURCE = FIELD.RDB$FIELD_NAME order by PROC.RDB$PROCEDURE_NAME, PROCPARA.RDB$PARAMETER_TYPE desc, PROCPARA.RDB$PARAMETER_NUMBER ";
   public static final int procedureColumnUnknown = 0;
   public static final int procedureColumnIn = 1;
   public static final int procedureColumnInOut = 2;
   public static final int procedureColumnOut = 4;
   public static final int procedureColumnReturn = 5;
   public static final int procedureColumnResult = 3;
   public static final int procedureNoNulls = 0;
   public static final int procedureNullable = 1;
   public static final int procedureNullableUnknown = 2;
   private static final String GET_COLUMNS_START = "select  RF.RDB$RELATION_NAME as RELATION_NAME, RF.RDB$FIELD_NAME as FIELD_NAME, F.RDB$FIELD_TYPE as FIELD_TYPE, F.RDB$FIELD_SUB_TYPE as FIELD_SUB_TYPE, F.RDB$FIELD_PRECISION as FIELD_PRECISION, F.RDB$FIELD_SCALE as FIELD_SCALE, F.RDB$FIELD_LENGTH as FIELD_LENGTH, RF.RDB$DESCRIPTION, RF.RDB$DEFAULT_SOURCE, RF.RDB$FIELD_POSITION as FIELD_POSITION,  RF.RDB$NULL_FLAG as NULL_FLAG from RDB$RELATION_FIELDS RF, RDB$FIELDS F where ";
   public static final String GET_COLUMNS_END = " RF.RDB$FIELD_SOURCE = F.RDB$FIELD_NAME order by 1, 10";
   public static final int columnNoNulls = 0;
   public static final int columnNullable = 1;
   public static final int columnNullableUnknown = 2;
   private static final String GET_COLUMN_PRIVILEGES_START = "select RF.RDB$RELATION_NAME as TABLE_NAME, RF.RDB$FIELD_NAME as COLUMN_NAME, UP.RDB$GRANTOR as GRANTOR, UP.RDB$USER as GRANTEE, UP.RDB$PRIVILEGE as PRIVILEGE, UP.RDB$GRANT_OPTION as IS_GRANTABLE from RDB$RELATION_FIELDS RF, RDB$FIELDS F, RDB$USER_PRIVILEGES UP where RF.RDB$RELATION_NAME = UP.RDB$RELATION_NAME and RF.RDB$FIELD_SOURCE = F.RDB$FIELD_NAME  and (UP.RDB$FIELD_NAME is null or UP.RDB$FIELD_NAME = RF.RDB$FIELD_NAME) and UP.RDB$RELATION_NAME = ? and ((";
   private static final String GET_COLUMN_PRIVILEGES_END = " UP.RDB$OBJECT_TYPE = 0) or (RF.RDB$FIELD_NAME is null and UP.RDB$OBJECT_TYPE = 0)) order by 3 ";
   private static final String GET_TABLE_PRIVILEGES_START = "select null as TABLE_CAT,  null as TABLE_SCHEM, RDB$RELATION_NAME as TABLE_NAME, RDB$GRANTOR as GRANTOR,  RDB$USER as GRANTEE,  RDB$PRIVILEGE as PRIVILEGE,  RDB$GRANT_OPTION as IS_GRANTABLE from RDB$USER_PRIVILEGES where ";
   private static final String GET_TABLE_PRIVILEGES_END = " RDB$OBJECT_TYPE = 0 and RDB$FIELD_NAME is null order by 1, 4";
   private static final String GET_BEST_ROW_IDENT = "select RF.RDB$FIELD_NAME, F.RDB$FIELD_TYPE, F.RDB$FIELD_SUB_TYPE, F.RDB$FIELD_SCALE, F.RDB$FIELD_LENGTH  from RDB$RELATION_CONSTRAINTS RC, RDB$INDEX_SEGMENTS ISGMT, RDB$RELATION_FIELDS RF,RDB$FIELDS F where RC.RDB$RELATION_NAME = ? and RC.RDB$CONSTRAINT_TYPE = 'PRIMARY KEY' and ISGMT.RDB$INDEX_NAME = RC.RDB$INDEX_NAME and RF.RDB$FIELD_NAME = ISGMT.RDB$FIELD_NAME and RF.RDB$RELATION_NAME = RC.RDB$RELATION_NAME and F.RDB$FIELD_NAME = RF.RDB$FIELD_SOURCE union all select RF.RDB$FIELD_NAME,F.RDB$FIELD_TYPE,F.RDB$FIELD_SUB_TYPE, F.RDB$FIELD_SCALE,F.RDB$FIELD_LENGTH from RDB$INDICES IND,RDB$INDEX_SEGMENTS ISGMT, RDB$RELATION_FIELDS RF,RDB$FIELDS F where not exists  (select * from RDB$RELATION_CONSTRAINTS where RDB$RELATION_NAME = ? and RDB$CONSTRAINT_TYPE = 'PRIMARY KEY') and IND.RDB$INDEX_NAME in (select max(RDB$INDEX_NAME) from RDB$INDICES where RDB$RELATION_NAME = ? and RDB$UNIQUE_FLAG   = 1) and ISGMT.RDB$INDEX_NAME = IND.RDB$INDEX_NAME and RF.RDB$FIELD_NAME = ISGMT.RDB$FIELD_NAME and RF.RDB$RELATION_NAME =  IND.RDB$RELATION_NAME and  F.RDB$FIELD_NAME = RF.RDB$FIELD_SOURCE order by 1";
   public static final int bestRowTemporary = 0;
   public static final int bestRowTransaction = 1;
   public static final int bestRowSession = 2;
   public static final int bestRowUnknown = 0;
   public static final int bestRowNotPseudo = 1;
   public static final int bestRowPseudo = 2;
   private static final String GET_VERSION_COLUMN = "select RF.RDB$FIELD_NAME, F.RDB$FIELD_TYPE, F.RDB$FIELD_SUB_TYPE, F.RDB$FIELD_SCALE, F.RDB$FIELD_LENGTH, F.RDB$CHARACTER_LENGTH  from  RDB$FIELDS F, RDB$RELATION_FIELDS RF  where 0 = 1 and  RF.RDB$RELATION_NAME = ? and RF.RDB$FIELD_SOURCE = F.RDB$FIELD_NAME and F.RDB$COMPUTED_BLR IS NOT NULL";
   public static final int versionColumnUnknown = 0;
   public static final int versionColumnNotPseudo = 1;
   public static final int versionColumnPseudo = 2;
   private static final String GET_PRIMARY_KEYS_START = "select null as TABLE_CAT, null as TABLE_SCHEM, RC.RDB$RELATION_NAME as TABLE_NAME, ISGMT.RDB$FIELD_NAME as COLUMN_NAME, CAST ((ISGMT.RDB$FIELD_POSITION + 1) as SMALLINT) as KEY_SEQ, RC.RDB$CONSTRAINT_NAME as PK_NAME from RDB$RELATION_CONSTRAINTS RC, RDB$INDEX_SEGMENTS ISGMT where ";
   private static final String GET_PRIMARY_KEYS_END = "RC.RDB$INDEX_NAME = ISGMT.RDB$INDEX_NAME and RC.RDB$CONSTRAINT_TYPE = 'PRIMARY KEY' order by ISGMT.RDB$FIELD_NAME ";
   private static final String GET_IMPORTED_KEYS_START = "select PK.RDB$RELATION_NAME as PKTABLE_NAME  ,ISP.RDB$FIELD_NAME as PKCOLUMN_NAME  ,FK.RDB$RELATION_NAME as FKTABLE_NAME  ,ISF.RDB$FIELD_NAME as FKCOLUMN_NAME  ,CAST ((ISP.RDB$FIELD_POSITION + 1) as SMALLINT) as KEY_SEQ  ,PRC.RDB$UPDATE_RULE as UPDATE_RULE  ,PRC.RDB$DELETE_RULE as DELETE_RULE  ,PK.RDB$CONSTRAINT_NAME as PK_NAME  ,FK.RDB$CONSTRAINT_NAME as FK_NAME  from  RDB$RELATION_CONSTRAINTS PK  ,RDB$RELATION_CONSTRAINTS FK  ,RDB$REF_CONSTRAINTS FRC  ,RDB$REF_CONSTRAINTS PRC  ,RDB$INDEX_SEGMENTS ISP  ,RDB$INDEX_SEGMENTS ISF  WHERE ";
   private static final String GET_IMPORTED_KEYS_END = " FK.RDB$CONSTRAINT_TYPE = 'FOREIGN KEY' and FK.RDB$CONSTRAINT_NAME = FRC.RDB$CONSTRAINT_NAME and FRC.RDB$CONST_NAME_UQ = PK.RDB$CONSTRAINT_NAME and PK.RDB$CONSTRAINT_TYPE = 'PRIMARY KEY' and PK.RDB$INDEX_NAME = ISP.RDB$INDEX_NAME and ISF.RDB$INDEX_NAME = FK.RDB$INDEX_NAME   and ISP.RDB$FIELD_POSITION = ISF.RDB$FIELD_POSITION  and PRC.RDB$CONSTRAINT_NAME = FK.RDB$CONSTRAINT_NAME  order by 1,5";
   public static final int importedKeyCascade = 0;
   public static final int importedKeyRestrict = 1;
   public static final int importedKeySetNull = 2;
   public static final int importedKeyNoAction = 3;
   public static final int importedNoAction = 3;
   public static final int importedKeySetDefault = 4;
   public static final int importedKeyInitiallyDeferred = 5;
   public static final int importedKeyInitiallyImmediate = 6;
   public static final int importedKeyNotDeferrable = 7;
   private static final String GET_EXPORTED_KEYS_START = "select PK.RDB$RELATION_NAME as PKTABLE_NAME  ,ISP.RDB$FIELD_NAME as PKCOLUMN_NAME  ,FK.RDB$RELATION_NAME as FKTABLE_NAME  ,ISF.RDB$FIELD_NAME as FKCOLUMN_NAME  ,CAST ((ISP.RDB$FIELD_POSITION + 1) as SMALLINT) as KEY_SEQ  ,FRC.RDB$UPDATE_RULE as UPDATE_RULE  ,FRC.RDB$DELETE_RULE as DELETE_RULE  ,PK.RDB$CONSTRAINT_NAME as PK_NAME  ,FK.RDB$CONSTRAINT_NAME as FK_NAME  from  RDB$RELATION_CONSTRAINTS PK  ,RDB$RELATION_CONSTRAINTS FK  ,RDB$REF_CONSTRAINTS FRC  ,RDB$REF_CONSTRAINTS PRC  ,RDB$INDEX_SEGMENTS ISP  ,RDB$INDEX_SEGMENTS ISF  WHERE ";
   private static final String GET_EXPORTED_KEYS_END = " PK.RDB$CONSTRAINT_TYPE = 'PRIMARY KEY' and FRC.RDB$CONST_NAME_UQ = PK.RDB$CONSTRAINT_NAME and  FK.RDB$CONSTRAINT_NAME = FRC.RDB$CONSTRAINT_NAME and FK.RDB$CONSTRAINT_TYPE = 'FOREIGN KEY' and PK.RDB$INDEX_NAME = ISP.RDB$INDEX_NAME and ISF.RDB$INDEX_NAME = FK.RDB$INDEX_NAME   and ISP.RDB$FIELD_POSITION = ISF.RDB$FIELD_POSITION  and PRC.RDB$CONSTRAINT_NAME = FK.RDB$CONSTRAINT_NAME order by 3,5";
   private static final String GET_CROSS_KEYS_START = "select PK.RDB$RELATION_NAME as PKTABLE_NAME  ,ISP.RDB$FIELD_NAME as PKCOLUMN_NAME , FK.RDB$RELATION_NAME as FKTABLE_NAME  ,ISF.RDB$FIELD_NAME as FKCOLUMN_NAME  ,CAST ((ISP.RDB$FIELD_POSITION + 1) as SMALLINT) as KEY_SEQ  ,RC.RDB$UPDATE_RULE as UPDATE_RULE  ,RC.RDB$DELETE_RULE as DELETE_RULE  ,PK.RDB$CONSTRAINT_NAME as PK_NAME  ,FK.RDB$CONSTRAINT_NAME as FK_NAME  from  RDB$RELATION_CONSTRAINTS PK  ,RDB$RELATION_CONSTRAINTS FK  ,RDB$REF_CONSTRAINTS RC  ,RDB$INDEX_SEGMENTS ISP  ,RDB$INDEX_SEGMENTS ISF  WHERE ";
   private static final String GET_CROSS_KEYS_END = " FK.RDB$CONSTRAINT_NAME = RC.RDB$CONSTRAINT_NAME  and PK.RDB$CONSTRAINT_NAME = RC.RDB$CONST_NAME_UQ  and ISP.RDB$INDEX_NAME = PK.RDB$INDEX_NAME  and ISF.RDB$INDEX_NAME = FK.RDB$INDEX_NAME  and ISP.RDB$FIELD_POSITION = ISF.RDB$FIELD_POSITION order by 3,5";
   private static final Short shortZero = new Short((short)0);
   private static final Short CASESENSITIVE = new Short((short)1);
   private static final Short CASEINSENSITIVE = new Short((short)0);
   private static final Short UNSIGNED = new Short((short)1);
   private static final Short SIGNED = new Short((short)0);
   private static final Short FIXEDSCALE = new Short((short)1);
   private static final Short VARIABLESCALE = new Short((short)0);
   private static final Short NOTAUTOINC = new Short((short)0);
   private static final Short PREDNONE = new Short((short)0);
   private static final Short PREDBASIC = new Short((short)2);
   private static final Short SEARCHABLE = new Short((short)3);
   private static final Short NULLABLE = new Short((short)1);
   private static final Integer BINARY = new Integer(2);
   private static final Integer DECIMAL = new Integer(10);
   public static final int typeNoNulls = 0;
   public static final int typeNullable = 1;
   public static final int typeNullableUnknown = 2;
   public static final int typePredNone = 0;
   public static final int typePredChar = 1;
   public static final int typePredBasic = 2;
   public static final int typeSearchable = 3;
   private static final String GET_INDEX_INFO_START = "select ind.RDB$RELATION_NAME AS TABLE_NAME  ,ind.RDB$UNIQUE_FLAG AS NON_UNIQUE  ,ind.RDB$INDEX_NAME as INDEX_NAME  ,ise.rdb$field_position as ORDINAL_POSITION  ,ise.rdb$field_name as COLUMN_NAME  ,ind.rdb$index_type as ASC_OR_DESC  ,COUNT (DISTINCT P.RDB$PAGE_NUMBER) as IN_PAGES  from rdb$indices ind, rdb$index_segments ise,RDB$PAGES P, RDB$RELATIONS R  where ind.rdb$index_name = ise.rdb$index_name  and ind.rdb$relation_name = ?   AND P.RDB$RELATION_ID = R.RDB$RELATION_ID  AND R.RDB$RELATION_NAME = IND.RDB$RELATION_NAME  AND (P.RDB$PAGE_TYPE = 7 OR P.RDB$PAGE_TYPE = 6) ";
   private static final String GET_INDEX_INFO_END = " group by IND.RDB$INDEX_NAME, IND.RDB$RELATION_NAME,  IND.RDB$UNIQUE_FLAG, ISE.RDB$FIELD_POSITION, ISE.RDB$FIELD_NAME,  IND.RDB$INDEX_TYPE, IND.RDB$SEGMENT_COUNT  order by 2, 3, 4";
   public static final short tableIndexStatistic = 0;
   public static final short tableIndexClustered = 1;
   public static final short tableIndexHashed = 2;
   public static final short tableIndexOther = 3;
   short attributeNoNulls = 0;
   short attributeNullable = 1;
   short attributeNullableUnknown = 2;
   int sqlStateXOpen = 1;
   int sqlStateSQL99 = 2;

   DatabaseMetaData(interbase.interclient.Connection var1) {
      this.connection_ = var1;
   }

   protected void finalize() throws Throwable {
      this.close();
      super.finalize();
   }

   private void close() {
      if (this.rs_ != null) {
         try {
            this.rs_.local_Close();
         } catch (SQLException var6) {
            ;
         } finally {
            this.rs_ = null;
         }
      }

   }

   void checkForClosedConnection() throws SQLException {
      if (!this.connection_.open_) {
         throw new InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__connection_closed__);
      }
   }

   public synchronized boolean allProceduresAreCallable() throws SQLException {
      this.checkForClosedConnection();
      if (this.userName_.equals("SYSDBA")) {
         return true;
      } else {
         this.pstmt_ = (interbase.interclient.PreparedStatement)this.connection_.prepareStatement(3, "select P.RDB$PROCEDURE_NAME from RDB$PROCEDURES P where not exists ( select UP.RDB$RELATION_NAME from RDB$USER_PRIVILEGES UP where (UP.RDB$USER = ? or UP.RDB$USER = 'PUBLIC') and UP.RDB$RELATION_NAME = P.RDB$PROCEDURE_NAME and UP.RDB$OBJECT_TYPE = 5 and UP.RDB$PRIVILEGE = 'X' )");
         this.pstmt_.setString(1, this.userName_);
         this.rs_ = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
         boolean var1 = !this.rs_.next();
         this.rs_.close();
         return var1;
      }
   }

   public synchronized boolean allTablesAreSelectable() throws SQLException {
      this.checkForClosedConnection();
      if (this.userName_.equals("SYSDBA")) {
         return true;
      } else {
         this.pstmt_ = (interbase.interclient.PreparedStatement)this.connection_.prepareStatement(3, "select REL.RDB$RELATION_NAME from RDB$RELATIONS REL where (REL.RDB$SYSTEM_FLAG = 0) and  not exists (  select UP.RDB$RELATION_NAME from  RDB$USER_PRIVILEGES UP  where UP.RDB$OBJECT_TYPE = 0 and UP.RDB$USER_TYPE = 8 and UP.RDB$RELATION_NAME = REL.RDB$RELATION_NAME and  (RDB$USER = ? or RDB$USER = 'PUBLIC') and (RDB$PRIVILEGE IN ('S',  'A')))");
         this.pstmt_.setString(1, this.userName_);
         this.rs_ = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
         boolean var1 = !this.rs_.next();
         this.rs_.close();
         return var1;
      }
   }

   public String getURL() throws SQLException {
      this.checkForClosedConnection();
      return "jdbc:interbase://" + this.connection_.serverName_ + "/" + this.connection_.database_;
   }

   public String getUserName() throws SQLException {
      this.checkForClosedConnection();
      return this.userName_;
   }

   public boolean isReadOnly() throws SQLException {
      this.checkForClosedConnection();
      return this.databaseReadOnly_;
   }

   public boolean nullsAreSortedHigh() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean nullsAreSortedLow() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean nullsAreSortedAtStart() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean nullsAreSortedAtEnd() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public String getDatabaseProductName() throws SQLException {
      this.checkForClosedConnection();
      return Globals.getResource("96");
   }

   public String getDatabaseProductVersion() throws SQLException {
      this.checkForClosedConnection();
      return this.databaseProductVersion_;
   }

   public int getDatabaseSQLDialect() throws SQLException {
      this.checkForClosedConnection();
      return this.databaseSQLDialect_;
   }

   public String getDriverName() throws SQLException {
      this.checkForClosedConnection();
      return Globals.getResource("94");
   }

   public String getDriverVersion() throws SQLException {
      this.checkForClosedConnection();
      return "4.8.1 for JRE 1.3, JRE 1.4 and J2SE 5 with InterBase 7.5";
   }

   public java.sql.ResultSet getDomains() {
      return null;
   }

   public int getDriverMajorVersion() {
      return 4;
   }

   public int getDriverMinorVersion() {
      return 8;
   }

   public boolean usesLocalFiles() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean usesLocalFilePerTable() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsMixedCaseIdentifiers() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean storesUpperCaseIdentifiers() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean storesLowerCaseIdentifiers() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean storesMixedCaseIdentifiers() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
      this.checkForClosedConnection();
      return this.odsMajorVersion_ > 10 || this.databaseSQLDialect_ >= 3;
   }

   public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
      this.checkForClosedConnection();
      return this.odsMajorVersion_ > 10 || this.databaseSQLDialect_ >= 3;
   }

   public String getIdentifierQuoteString() throws SQLException {
      this.checkForClosedConnection();
      return this.odsMajorVersion_ >= 10 && (this.databaseSQLDialect_ >= 3 || this.attachmentSQLDialect_ >= 2) ? "\"" : " ";
   }

   public String getSQLKeywords() throws SQLException {
      this.checkForClosedConnection();
      return "ACTIVE,AFTER,ASCENDING,BASE_NAME,BEFORE,BLOB,BOOLEAN,CACHE,CHECK_POINT_LENGTH,COMPUTED,CONDITIONAL,CONTAINING,CSTRING,DATABASE,RDB$DB_KEY,DEBUG,DESCENDING,DO,ENTRY_POINT,EXIT,FILE,FILTER,FUNCTION,GDSCODE,GENERATOR,GEN_ID,GROUP_COMMIT_WAIT_TIME,IF,INACTIVE,INPUT_TYPE,INDEX,LOGFILE,LOG_BUFFER_SIZE,MANUAL,MAXIMUM_SEGMENT,MERGE,MESSAGE,MODULE_NAME,NCHAR,NUM_LOG_BUFFERS,OUTPUT_TYPE,OVERFLOW,PAGE,PAGES,PAGE_SIZE,PARAMETER,PASSWORD,PLAN,POST_EVENT,PROTECTED,RAW_PARTITIONS,RESERV,RESERVING,RETAIN,RETURNING_VALUES,RETURNS,SEGMENT,SHADOW,SHARED,SINGULAR,SNAPSHOT,SORT,STABILITY,STARTS,STARTING,STATISTICS,SUB_TYPE,SUSPEND,TRIGGER,VARIABLE,RECORD_VERSION,WAIT,WHILE,WORK";
   }

   public String getNumericFunctions() throws SQLException {
      this.checkForClosedConnection();
      return "";
   }

   public String getStringFunctions() throws SQLException {
      this.checkForClosedConnection();
      return "LENGTH, SUBSTRING, CONCAT, LCASE, LTRIM, CHAR, ASCII, USER, UCASE, LTRIM RTRIM ";
   }

   public String getSystemFunctions() throws SQLException {
      this.checkForClosedConnection();
      return "";
   }

   public String getTimeDateFunctions() throws SQLException {
      this.checkForClosedConnection();
      return "";
   }

   public String getSearchStringEscape() throws SQLException {
      this.checkForClosedConnection();
      return "\\";
   }

   public String getExtraNameCharacters() throws SQLException {
      this.checkForClosedConnection();
      return "$";
   }

   public boolean supportsAlterTableWithAddColumn() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsAlterTableWithDropColumn() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsColumnAliasing() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean nullPlusNonNullIsNull() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsConvert() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsConvert(int var1, int var2) throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsTableCorrelationNames() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsDifferentTableCorrelationNames() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsExpressionsInOrderBy() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsOrderByUnrelated() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsGroupBy() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsGroupByUnrelated() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsGroupByBeyondSelect() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsLikeEscapeClause() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsMultipleResultSets() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsMultipleTransactions() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsNonNullableColumns() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsMinimumSQLGrammar() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsCoreSQLGrammar() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsExtendedSQLGrammar() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsANSI92EntryLevelSQL() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsANSI92IntermediateSQL() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsANSI92FullSQL() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsIntegrityEnhancementFacility() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsOuterJoins() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsFullOuterJoins() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsLimitedOuterJoins() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public String getSchemaTerm() throws SQLException {
      this.checkForClosedConnection();
      return "";
   }

   public String getProcedureTerm() throws SQLException {
      this.checkForClosedConnection();
      return "PROCEDURE";
   }

   public String getCatalogTerm() throws SQLException {
      this.checkForClosedConnection();
      return "";
   }

   public boolean isCatalogAtStart() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public String getCatalogSeparator() throws SQLException {
      this.checkForClosedConnection();
      return "";
   }

   public boolean supportsSchemasInDataManipulation() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsSchemasInProcedureCalls() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsSchemasInTableDefinitions() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsSchemasInIndexDefinitions() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsCatalogsInDataManipulation() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsCatalogsInProcedureCalls() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsCatalogsInTableDefinitions() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsPositionedDelete() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsPositionedUpdate() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsSelectForUpdate() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsStoredProcedures() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsSubqueriesInComparisons() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsSubqueriesInExists() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsSubqueriesInIns() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsSubqueriesInQuantifieds() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsCorrelatedSubqueries() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsUnion() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsUnionAll() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public int getMaxBinaryLiteralLength() throws SQLException {
      this.checkForClosedConnection();
      return 0;
   }

   public int getMaxCharLiteralLength() throws SQLException {
      this.checkForClosedConnection();
      return 1024;
   }

   public int getMaxColumnNameLength() throws SQLException {
      this.checkForClosedConnection();
      return 68;
   }

   public int getMaxColumnsInGroupBy() throws SQLException {
      this.checkForClosedConnection();
      return 16;
   }

   public int getMaxColumnsInIndex() throws SQLException {
      this.checkForClosedConnection();
      return 16;
   }

   public int getMaxColumnsInOrderBy() throws SQLException {
      this.checkForClosedConnection();
      return 16;
   }

   public int getMaxColumnsInSelect() throws SQLException {
      this.checkForClosedConnection();
      return 32767;
   }

   public int getMaxColumnsInTable() throws SQLException {
      this.checkForClosedConnection();
      return 32767;
   }

   public int getMaxConnections() throws SQLException {
      this.checkForClosedConnection();
      return 0;
   }

   public int getMaxCursorNameLength() throws SQLException {
      this.checkForClosedConnection();
      return 68;
   }

   public int getMaxIndexLength() throws SQLException {
      this.checkForClosedConnection();
      return 198;
   }

   public int getMaxSchemaNameLength() throws SQLException {
      this.checkForClosedConnection();
      return 0;
   }

   public int getMaxProcedureNameLength() throws SQLException {
      this.checkForClosedConnection();
      return 27;
   }

   public int getMaxCatalogNameLength() throws SQLException {
      this.checkForClosedConnection();
      return 0;
   }

   public int getMaxRowSize() throws SQLException {
      this.checkForClosedConnection();
      return 32664;
   }

   public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public int getMaxStatementLength() throws SQLException {
      this.checkForClosedConnection();
      return 640;
   }

   public int getMaxStatements() throws SQLException {
      this.checkForClosedConnection();
      return 0;
   }

   public int getMaxTableNameLength() throws SQLException {
      this.checkForClosedConnection();
      return 68;
   }

   public int getMaxTablesInSelect() throws SQLException {
      this.checkForClosedConnection();
      return 16;
   }

   public int getMaxUserNameLength() throws SQLException {
      this.checkForClosedConnection();
      return 68;
   }

   public int getDefaultTransactionIsolation() throws SQLException {
      this.checkForClosedConnection();
      Connection var10000 = this.connection_;
      return 8;
   }

   public boolean supportsTransactions() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsTransactionIsolationLevel(int var1) throws SQLException {
      this.checkForClosedConnection();
      switch(var1) {
      case 0:
      case 1:
         return false;
      case 2:
      case 4:
      case 8:
      case 16:
         return true;
      case 3:
      case 5:
      case 6:
      case 7:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      default:
         throw new InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__isolation_0__, String.valueOf(var1));
      }
   }

   public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
      this.checkForClosedConnection();
      return true;
   }

   public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
      this.checkForClosedConnection();
      return false;
   }

   private void systemTableQueryPreamble(String var1, String var2) throws SQLException {
   }

   private boolean exactMatch(String var1) {
      if (var1 == null) {
         return true;
      } else {
         int var2 = 0;

         while(true) {
            int var3;
            if ((var3 = var1.indexOf(37, var2)) < var1.length() && var3 != -1) {
               if (var3 != 0 && var1.charAt(var3 - 1) == '\\') {
                  ++var3;
                  var2 = var3;
                  continue;
               }

               return false;
            }

            return true;
         }
      }
   }

   public String stripEscape(String var1) {
      StringBuffer var2 = new StringBuffer(var1.length());

      for(int var3 = 0; var3 < var1.length(); ++var3) {
         if (var1.charAt(var3) != '\\') {
            var2.append(var1.charAt(var3));
         }
      }

      return var2.toString();
   }

   public synchronized java.sql.ResultSet getProcedures(String var1, String var2, String var3) throws SQLException {
      this.checkForClosedConnection();
      this.systemTableQueryPreamble(var1, var2);
      SqlPattern var4 = new SqlPattern("RDB$PROCEDURE_NAME", var3);
      String var5 = "select  RDB$PROCEDURE_NAME as PROCEDURE_NAME, RDB$DESCRIPTION as REMARKS, RDB$PROCEDURE_OUTPUTS as PROCEDURE_TYPE,RDB$OWNER_NAME from RDB$PROCEDURES where " + var4.getClause() + "1 = 1 order by 1";
      this.pstmt_ = (interbase.interclient.PreparedStatement)this.connection_.prepareStatement(3, var5);
      if (!"%".equals(var3) && !"".equals(var3)) {
         this.pstmt_.setString(1, var4.getPattern());
      }

      interbase.interclient.ResultSet var6 = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
      interbase.interclient.XSQLVAR[] var7 = new interbase.interclient.XSQLVAR[9];
      var7[0] = new interbase.interclient.XSQLVAR();
      var7[0].sqltype = 601;
      var7[0].sqllen = 68;
      var7[0].sqlind = -1;
      var7[0].sqlname = "PROCEDURE_CAT";
      var7[0].relname = "RDB$PROCEDURES";
      var7[1] = new interbase.interclient.XSQLVAR();
      var7[1].sqltype = 601;
      var7[1].sqllen = 68;
      var7[1].sqlind = -1;
      var7[1].sqlname = "PROCEDURE_SCHEM";
      var7[1].relname = "RDB$PROCEDURES";
      var7[2] = new interbase.interclient.XSQLVAR();
      var7[2].sqltype = 600;
      var7[2].sqllen = 68;
      var7[2].sqlind = 0;
      var7[2].sqlname = "PROCEDURE_NAME";
      var7[2].relname = "RDB$PROCEDURES";
      var7[3] = new interbase.interclient.XSQLVAR();
      var7[3].sqltype = 601;
      var7[3].sqllen = 68;
      var7[3].sqlind = 0;
      var7[3].sqlname = "reserved";
      var7[3].relname = "RDB$PROCEDURES";
      var7[4] = new interbase.interclient.XSQLVAR();
      var7[4].sqltype = 601;
      var7[4].sqllen = 68;
      var7[4].sqlind = 0;
      var7[4].sqlname = "reserved";
      var7[4].relname = "RDB$PROCEDURES";
      var7[5] = new interbase.interclient.XSQLVAR();
      var7[5].sqltype = 601;
      var7[5].sqllen = 68;
      var7[5].sqlind = 0;
      var7[5].sqlname = "reserved";
      var7[5].relname = "RDB$PROCEDURES";
      var7[6] = new interbase.interclient.XSQLVAR();
      var7[6].sqltype = 601;
      var7[6].sqllen = 68;
      var7[6].sqlind = 0;
      var7[6].sqlname = "REMARKS";
      var7[6].relname = "RDB$PROCEDURES";
      var7[7] = new interbase.interclient.XSQLVAR();
      var7[7].sqltype = 500;
      var7[7].sqlname = "PROCEDURE_TYPE";
      var7[7].relname = "RDB$PROCEDURES";
      var7[8] = new interbase.interclient.XSQLVAR();
      var7[8].sqltype = 600;
      var7[8].sqllen = 68;
      var7[8].sqlind = 0;
      var7[8].sqlname = "PROCEDURE_OWNER";
      var7[8].relname = "RDB$PROCEDURES";
      ArrayList var8 = new ArrayList();

      while(var6.next()) {
         Object[] var9 = new Object[]{null, null, var6.getString("PROCEDURE_NAME").trim(), null, null, null, new String(""), new Short((short)(var6.getShort("PROCEDURE_TYPE") > 0 ? 2 : 1)), var6.getString("RDB$OWNER_NAME")};
         var8.add(var9);
      }

      this.setCannedResult(var7, var8, 9);
      var6.close();
      this.pstmt_.close();
      return this.rs_;
   }

   public synchronized java.sql.ResultSet getProcedureColumns(String var1, String var2, String var3, String var4) throws SQLException {
      this.checkForClosedConnection();
      this.systemTableQueryPreamble(var1, var2);
      SqlPattern var5 = new SqlPattern("PROC.RDB$PROCEDURE_NAME", var3);
      SqlPattern var6 = new SqlPattern("PROCPARA.RDB$PARAMETER_NAME", var4);
      String var7 = "select PROC.RDB$PROCEDURE_NAME as PROCEDURE_NAME, PROCPARA.RDB$PARAMETER_NAME as COLUMN_NAME, PROCPARA.RDB$PARAMETER_TYPE as COLUMN_TYPE, FIELD.RDB$FIELD_TYPE AS FIELD_TYPE,  FIELD.RDB$FIELD_SUB_TYPE AS FIELD_SUB_TYPE,  FIELD.RDB$FIELD_PRECISION as FIELD_PRECISION, FIELD.RDB$FIELD_SCALE as FIELD_SCALE, FIELD.RDB$FIELD_LENGTH as FIELD_LENGTH, FIELD.RDB$NULL_FLAG as NULLABLE, PROCPARA.RDB$DESCRIPTION as REMARKS from RDB$PROCEDURES PROC, RDB$PROCEDURE_PARAMETERS PROCPARA, RDB$FIELDS FIELD where " + var5.getClause() + var6.getClause() + " PROC.RDB$PROCEDURE_NAME = PROCPARA.RDB$PROCEDURE_NAME and PROCPARA.RDB$FIELD_SOURCE = FIELD.RDB$FIELD_NAME order by PROC.RDB$PROCEDURE_NAME, PROCPARA.RDB$PARAMETER_TYPE desc, PROCPARA.RDB$PARAMETER_NUMBER ";
      this.pstmt_ = (interbase.interclient.PreparedStatement)this.connection_.prepareStatement(3, var7);
      int var8 = 1;
      if (!"%".equals(var3) && !"".equals(var3)) {
         this.pstmt_.setString(var8, var5.getPattern());
         ++var8;
      }

      if (!"%".equals(var4) && !"".equals(var4)) {
         this.pstmt_.setString(var8, var6.getPattern());
      }

      interbase.interclient.ResultSet var9 = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
      interbase.interclient.XSQLVAR[] var10 = new interbase.interclient.XSQLVAR[13];
      var10[0] = new interbase.interclient.XSQLVAR();
      var10[0].sqltype = 601;
      var10[0].sqllen = 68;
      var10[0].sqlind = -1;
      var10[0].sqlname = "PROCEDURE_CAT";
      var10[0].relname = "RDB$PROCCOLUMNINFO";
      var10[1] = new interbase.interclient.XSQLVAR();
      var10[1].sqltype = 601;
      var10[1].sqllen = 68;
      var10[1].sqlind = -1;
      var10[1].sqlname = "PROCEDURE_SCHEM";
      var10[1].relname = "RDB$PROCCOLUMNINFO";
      var10[2] = new interbase.interclient.XSQLVAR();
      var10[2].sqltype = 600;
      var10[2].sqllen = 68;
      var10[2].sqlind = 0;
      var10[2].sqlname = "PROCEDURE_NAME";
      var10[2].relname = "RDB$PROCCOLUMNINFO";
      var10[3] = new interbase.interclient.XSQLVAR();
      var10[3].sqltype = 600;
      var10[3].sqllen = 68;
      var10[3].sqlind = 0;
      var10[3].sqlname = "COLUMN_NAME";
      var10[3].relname = "RDB$PROCCOLUMNINFO";
      var10[4] = new interbase.interclient.XSQLVAR();
      var10[4].sqltype = 500;
      var10[4].sqlname = "COLUMN_TYPE";
      var10[4].relname = "RDB$PROCCOLUMNINFO";
      var10[5] = new interbase.interclient.XSQLVAR();
      var10[5].sqltype = 500;
      var10[5].sqlname = "DATA_TYPE";
      var10[5].relname = "RDB$PROCCOLUMNINFO";
      var10[6] = new interbase.interclient.XSQLVAR();
      var10[6].sqltype = 600;
      var10[6].sqllen = 68;
      var10[6].sqlind = 0;
      var10[6].sqlname = "TYPE_NAME";
      var10[6].relname = "RDB$PROCCOLUMNINFO";
      var10[7] = new interbase.interclient.XSQLVAR();
      var10[7].sqltype = 496;
      var10[7].sqlname = "PRECISION";
      var10[7].relname = "RDB$PROCCOLUMNINFO";
      var10[8] = new interbase.interclient.XSQLVAR();
      var10[8].sqltype = 496;
      var10[8].sqlname = "LENGTH";
      var10[8].relname = "RDB$PROCCOLUMNINFO";
      var10[9] = new interbase.interclient.XSQLVAR();
      var10[9].sqltype = 500;
      var10[9].sqlname = "SCALE";
      var10[9].relname = "RDB$PROCCOLUMNINFO";
      var10[10] = new interbase.interclient.XSQLVAR();
      var10[10].sqltype = 500;
      var10[10].sqlname = "RADIX";
      var10[10].relname = "RDB$PROCCOLUMNINFO";
      var10[11] = new interbase.interclient.XSQLVAR();
      var10[11].sqltype = 500;
      var10[11].sqlname = "NULLABLE";
      var10[11].relname = "RDB$PROCCOLUMNINFO";
      var10[12] = new interbase.interclient.XSQLVAR();
      var10[12].sqltype = 601;
      var10[12].sqllen = 68;
      var10[12].sqlind = 0;
      var10[12].sqlname = "REMARKS";
      var10[12].relname = "RDB$PROCCOLUMNINFO";
      ArrayList var11 = new ArrayList();

      while(var9.next()) {
         Object[] var12 = new Object[18];
         var12[0] = null;
         var12[1] = null;
         var12[2] = var9.getString("PROCEDURE_NAME").trim();
         var12[3] = var9.getString("COLUMN_NAME").trim();
         var12[4] = new Short((short)(var9.getShort("COLUMN_TYPE") > 0 ? 3 : 1));
         short var13 = var9.getShort("FIELD_TYPE");
         short var14 = var9.getShort("FIELD_SUB_TYPE");
         short var15 = var9.getShort("FIELD_SCALE");
         int var16 = interbase.interclient.IBTypes.getDataType(var13, var14, var15);
         var12[5] = new Short((short)var16);
         int var17 = interbase.interclient.IBTypes.getIBType(var13, var14, var15);
         var12[6] = interbase.interclient.IBTypes.getIBTypeName(var17);
         short var18 = var9.getShort("FIELD_LENGTH");
         var12[7] = new Integer(var9.getInt("FIELD_PRECISION"));
         var12[8] = new Integer(var18);
         var12[9] = new Short((short)Math.abs(var15));
         var12[10] = new Short(interbase.interclient.IBTypes.getRadix(var16));
         var12[11] = new Short((short)(var9.getShort("NULLABLE") == 1 ? 0 : 1));
         String var19 = var9.getString("REMARKS");
         var12[12] = var19 == null ? new String("") : var19;
         var11.add(var12);
      }

      this.setCannedResult(var10, var11, 13);
      var9.close();
      this.pstmt_.close();
      return this.rs_;
   }

   public synchronized java.sql.ResultSet getTables(String var1, String var2, String var3, String[] var4) throws SQLException {
      this.checkForClosedConnection();
      this.systemTableQueryPreamble(var1, var2);
      boolean var7 = false;
      boolean var6 = false;
      boolean var5 = false;
      String var8;
      if (var3 == null) {
         var8 = new String("%");
      } else {
         var8 = new String(this.systemTableValue(var3));
      }

      if (var4 == null) {
         var7 = true;
         var6 = true;
         var5 = true;
      } else {
         for(int var9 = 0; var9 < var4.length; ++var9) {
            if ("TABLE".equals(var4[var9])) {
               var6 = true;
            } else if ("SYSTEM TABLE".equals(var4[var9])) {
               var5 = true;
            } else if ("VIEW".equals(var4[var9])) {
               var7 = true;
            }
         }
      }

      StringBuffer var17 = new StringBuffer("");
      boolean var10 = false;
      boolean var11 = true;
      int var12 = 0;
      if (!"%".equals(var8) && !"".equals(var8)) {
         if (this.exactMatch(var8)) {
            var8 = this.systemTableValue(this.stripEscape(var8));
            if (var5) {
               var17.append("select  cast(RDB$RELATION_NAME as VARCHAR(31)) as TABLE_NAME, cast('SYSTEM TABLE' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where  RDB$SYSTEM_FLAG = 1 and RDB$VIEW_SOURCE is null and ? = RDB$RELATION_NAME ");
               var11 = false;
               ++var12;
            }

            if (var6) {
               var17.append(var11 ? "" : "UNION ");
               var17.append(" select  cast(RDB$RELATION_NAME as varchar(31)) as TABLE_NAME, cast('TABLE' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$SYSTEM_FLAG = 0 and RDB$VIEW_SOURCE is null and ? = RDB$RELATION_NAME ");
               var11 = false;
               ++var12;
            }

            if (var7) {
               var17.append(var11 ? "" : "UNION ");
               var17.append(" select  cast(RDB$RELATION_NAME as varchar(31)) as TABLE_NAME, cast('VIEW' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$VIEW_SOURCE is not null and ? = RDB$RELATION_NAME");
               ++var12;
            }
         } else {
            var8 = this.systemTableValue(var8) + "                               " + "%";
            if (var5) {
               var17.append("select  cast(RDB$RELATION_NAME as VARCHAR(31)) as TABLE_NAME, cast('SYSTEM TABLE' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$SYSTEM_FLAG = 1 and RDB$VIEW_SOURCE is null and RDB$RELATION_NAME || '                               ' like ? escape '\\' ");
               var11 = false;
               ++var12;
            }

            if (var6) {
               var17.append(var11 ? "" : "UNION ");
               var17.append(" select  cast(RDB$RELATION_NAME as VARCHAR(31)) as TABLE_NAME, cast('TABLE' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$SYSTEM_FLAG = 0 and RDB$VIEW_SOURCE is null and RDB$RELATION_NAME || '                               ' like ? escape '\\'");
               var11 = false;
               ++var12;
            }

            if (var7) {
               var17.append(var11 ? "" : "UNION ");
               var17.append(" select  cast(RDB$RELATION_NAME as VARCHAR(31)) as TABLE_NAME, cast('VIEW' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$VIEW_SOURCE is not null and RDB$RELATION_NAME || '                               ' like ? escape '\\' ");
               ++var12;
            }
         }
      } else {
         if (var5) {
            var17.append("select  cast(RDB$RELATION_NAME as VARCHAR(31)) as TABLE_NAME, cast('SYSTEM TABLE' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$SYSTEM_FLAG = 1 and RDB$VIEW_SOURCE is null ");
            var11 = false;
         }

         if (var6) {
            var17.append(var11 ? "" : "UNION ");
            var17.append(" select  cast(RDB$RELATION_NAME as VARCHAR(31)) as TABLE_NAME, cast('TABLE' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$SYSTEM_FLAG = 0 and RDB$VIEW_SOURCE is null ");
            var11 = false;
         }

         if (var7) {
            var17.append(var11 ? "" : "UNION ");
            var17.append(" select  cast(RDB$RELATION_NAME as VARCHAR(31)) as TABLE_NAME, cast('VIEW' as varchar(31)) as TABLE_TYPE, RDB$DESCRIPTION as REMARKS, RDB$OWNER_NAME as OWNER_NAME from RDB$RELATIONS where RDB$VIEW_SOURCE is not null");
         }

         var10 = true;
      }

      this.pstmt_ = (interbase.interclient.PreparedStatement)this.connection_.prepareStatement(3, var17.toString());
      if (!var10) {
         for(int var13 = 0; var13 < var12; ++var13) {
            this.pstmt_.setString(var13 + 1, var8);
         }
      }

      interbase.interclient.ResultSet var18 = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
      interbase.interclient.XSQLVAR[] var14 = new interbase.interclient.XSQLVAR[6];
      var14[0] = new interbase.interclient.XSQLVAR();
      var14[0].sqltype = 601;
      var14[0].sqllen = 68;
      var14[0].sqlind = -1;
      var14[0].sqlname = "TABLE_CAT";
      var14[0].relname = "RDB$TABLEINFO";
      var14[1] = new interbase.interclient.XSQLVAR();
      var14[1].sqltype = 601;
      var14[1].sqllen = 68;
      var14[1].sqlind = -1;
      var14[1].sqlname = "TABLE_SCHEM";
      var14[1].relname = "RDB$TABLEINFO";
      var14[2] = new interbase.interclient.XSQLVAR();
      var14[2].sqltype = 600;
      var14[2].sqllen = 68;
      var14[2].sqlind = 0;
      var14[2].sqlname = "TABLE_NAME";
      var14[2].relname = "RDB$TABLEINFO";
      var14[3] = new interbase.interclient.XSQLVAR();
      var14[3].sqltype = 600;
      var14[3].sqllen = 68;
      var14[3].sqlind = 0;
      var14[3].sqlname = "TABLE_TYPE";
      var14[3].relname = "RDB$TABLEINFO";
      var14[4] = new interbase.interclient.XSQLVAR();
      var14[4].sqltype = 601;
      var14[4].sqllen = 68;
      var14[4].sqlind = 0;
      var14[4].sqlname = "REMARKS";
      var14[4].relname = "RDB$TABLEINFO";
      var14[5] = new interbase.interclient.XSQLVAR();
      var14[5].sqltype = 600;
      var14[5].sqllen = 68;
      var14[5].sqlind = 0;
      var14[5].sqlname = "TABLE_OWNER";
      var14[5].relname = "RDB$TABLEINFO";
      ArrayList var15 = new ArrayList();

      while(var18.next()) {
         Object[] var16 = new Object[]{null, null, var18.getString("TABLE_NAME").trim(), var18.getString("TABLE_TYPE").trim(), new String(""), var18.getString("OWNER_NAME").trim()};
         var15.add(var16);
      }

      this.setCannedResult(var14, var15, 6);
      var18.close();
      this.pstmt_.close();
      return this.rs_;
   }

   public java.sql.ResultSet getSchemas() throws SQLException {
      this.checkForClosedConnection();
      interbase.interclient.XSQLVAR[] var1 = new interbase.interclient.XSQLVAR[]{new interbase.interclient.XSQLVAR()};
      var1[0].sqltype = 601;
      var1[0].sqllen = 68;
      var1[0].sqlind = -1;
      var1[0].sqlname = "TABLE_SCHEM";
      var1[0].relname = "RDB$TABLESCHEM";
      ArrayList var2 = new ArrayList(0);
      this.setCannedResult(var1, var2, 1);
      return this.rs_;
   }

   public java.sql.ResultSet getCatalogs() throws SQLException {
      this.checkForClosedConnection();
      interbase.interclient.XSQLVAR[] var1 = new interbase.interclient.XSQLVAR[]{new interbase.interclient.XSQLVAR()};
      var1[0].sqltype = 601;
      var1[0].sqllen = 68;
      var1[0].sqlind = -1;
      var1[0].sqlname = "TABLE_CAT";
      var1[0].relname = "RDB$TABLECAT";
      ArrayList var2 = new ArrayList(0);
      this.setCannedResult(var1, var2, 1);
      return this.rs_;
   }

   public synchronized java.sql.ResultSet getTableTypes() throws SQLException {
      this.checkForClosedConnection();
      interbase.interclient.XSQLVAR[] var1 = new interbase.interclient.XSQLVAR[]{new interbase.interclient.XSQLVAR()};
      var1[0].sqltype = 600;
      var1[0].sqllen = 68;
      var1[0].sqlind = -1;
      var1[0].sqlname = "TABLE_TYPE";
      var1[0].relname = "RDB$TABLETYPES";
      ArrayList var2 = new ArrayList(ALL_TYPES.length);

      for(int var3 = 0; var3 < ALL_TYPES.length; ++var3) {
         var2.add(new Object[]{ALL_TYPES[var3]});
      }

      this.setCannedResult(var1, var2, 1);
      return this.rs_;
   }

   private void setCannedResult(interbase.interclient.XSQLVAR[] var1, ArrayList var2, int var3) throws SQLException {
      interbase.interclient.Statement var4 = new Statement(this.connection_);
      var4.setCannedRows(var2);
      interbase.interclient.XSQLDA var5 = new interbase.interclient.XSQLDA(var3, var1);
      this.rs_ = new interbase.interclient.ResultSet(var4, false, var3, var5, 1003);
      this.rs_.setResultSetMetaData(var5, true);
   }

   public synchronized java.sql.ResultSet getColumns(String var1, String var2, String var3, String var4) throws SQLException {
      this.checkForClosedConnection();
      this.systemTableQueryPreamble(var1, var2);
      SqlPattern var5 = new SqlPattern("RF.RDB$FIELD_NAME", var4);
      SqlPattern var6 = new SqlPattern("RF.RDB$RELATION_NAME", var3);
      String var7 = "select  RF.RDB$RELATION_NAME as RELATION_NAME, RF.RDB$FIELD_NAME as FIELD_NAME, F.RDB$FIELD_TYPE as FIELD_TYPE, F.RDB$FIELD_SUB_TYPE as FIELD_SUB_TYPE, F.RDB$FIELD_PRECISION as FIELD_PRECISION, F.RDB$FIELD_SCALE as FIELD_SCALE, F.RDB$FIELD_LENGTH as FIELD_LENGTH, RF.RDB$DESCRIPTION, RF.RDB$DEFAULT_SOURCE, RF.RDB$FIELD_POSITION as FIELD_POSITION,  RF.RDB$NULL_FLAG as NULL_FLAG from RDB$RELATION_FIELDS RF, RDB$FIELDS F where " + var6.getClause() + var5.getClause() + " RF.RDB$FIELD_SOURCE = F.RDB$FIELD_NAME order by 1, 10";
      this.pstmt_ = (interbase.interclient.PreparedStatement)this.connection_.prepareStatement(3, var7);
      int var8 = 1;
      if (!"%".equals(var3) && !"".equals(var3)) {
         this.pstmt_.setString(var8, var6.getPattern());
         ++var8;
      }

      if (!"%".equals(var4) && !"".equals(var4)) {
         this.pstmt_.setString(var8, var5.getPattern());
      }

      interbase.interclient.ResultSet var9 = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
      interbase.interclient.XSQLVAR[] var10 = new interbase.interclient.XSQLVAR[18];
      var10[0] = new interbase.interclient.XSQLVAR();
      var10[0].sqltype = 601;
      var10[0].sqllen = 68;
      var10[0].sqlind = -1;
      var10[0].sqlname = "TABLE_CAT";
      var10[0].relname = "COLUMNINFO";
      var10[1] = new interbase.interclient.XSQLVAR();
      var10[1].sqltype = 601;
      var10[1].sqllen = 68;
      var10[1].sqlind = -1;
      var10[1].sqlname = "TABLE_SCHEM";
      var10[1].relname = "COLUMNINFO";
      var10[2] = new interbase.interclient.XSQLVAR();
      var10[2].sqltype = 600;
      var10[2].sqllen = 68;
      var10[2].sqlind = 0;
      var10[2].sqlname = "TABLE_NAME";
      var10[2].relname = "COLUMNINFO";
      var10[3] = new interbase.interclient.XSQLVAR();
      var10[3].sqltype = 600;
      var10[3].sqllen = 68;
      var10[3].sqlind = 0;
      var10[3].sqlname = "COLUMN_NAME";
      var10[3].relname = "COLUMNINFO";
      var10[4] = new interbase.interclient.XSQLVAR();
      var10[4].sqltype = 500;
      var10[4].sqlname = "DATA_TYPE";
      var10[4].relname = "COLUMNINFO";
      var10[5] = new interbase.interclient.XSQLVAR();
      var10[5].sqltype = 600;
      var10[5].sqllen = 68;
      var10[5].sqlind = 0;
      var10[5].sqlname = "TYPE_NAME";
      var10[5].relname = "COLUMNINFO";
      var10[6] = new interbase.interclient.XSQLVAR();
      var10[6].sqltype = 496;
      var10[6].sqlname = "COLUMN_SIZE";
      var10[6].relname = "COLUMNINFO";
      var10[7] = new interbase.interclient.XSQLVAR();
      var10[7].sqltype = 497;
      var10[7].sqlname = "BUFFER_LENGTH";
      var10[7].relname = "COLUMNINFO";
      var10[8] = new interbase.interclient.XSQLVAR();
      var10[8].sqltype = 496;
      var10[8].sqlname = "DECIMAL_DIGITS";
      var10[8].relname = "COLUMNINFO";
      var10[9] = new interbase.interclient.XSQLVAR();
      var10[9].sqltype = 496;
      var10[9].sqlname = "NUM_PREC_RADIX";
      var10[9].relname = "COLUMNINFO";
      var10[10] = new interbase.interclient.XSQLVAR();
      var10[10].sqltype = 496;
      var10[10].sqlname = "NULLABLE";
      var10[10].relname = "COLUMNINFO";
      var10[11] = new interbase.interclient.XSQLVAR();
      var10[11].sqltype = 601;
      var10[11].sqllen = 68;
      var10[11].sqlind = 0;
      var10[11].sqlname = "REMARKS";
      var10[11].relname = "COLUMNINFO";
      var10[12] = new interbase.interclient.XSQLVAR();
      var10[12].sqltype = 601;
      var10[12].sqllen = 68;
      var10[12].sqlind = 0;
      var10[12].sqlname = "COLUMN_DEF";
      var10[12].relname = "COLUMNINFO";
      var10[13] = new interbase.interclient.XSQLVAR();
      var10[13].sqltype = 497;
      var10[13].sqlname = "SQL_DATA_TYPE";
      var10[13].relname = "COLUMNINFO";
      var10[14] = new interbase.interclient.XSQLVAR();
      var10[14].sqltype = 497;
      var10[14].sqlname = "SQL_DATETIME_SUB";
      var10[14].relname = "COLUMNINFO";
      var10[15] = new interbase.interclient.XSQLVAR();
      var10[15].sqltype = 496;
      var10[15].sqlname = "CHAR_OCTET_LENGTH";
      var10[15].relname = "COLUMNINFO";
      var10[16] = new interbase.interclient.XSQLVAR();
      var10[16].sqltype = 496;
      var10[16].sqlname = "ORDINAL_POSITION";
      var10[16].relname = "COLUMNINFO";
      var10[17] = new interbase.interclient.XSQLVAR();
      var10[17].sqltype = 600;
      var10[17].sqllen = 3;
      var10[17].sqlind = 0;
      var10[17].sqlname = "IS_NULLABLE";
      var10[17].relname = "COLUMNINFO";
      ArrayList var11 = new ArrayList();

      while(var9.next()) {
         Object[] var12 = new Object[18];
         var12[0] = null;
         var12[1] = null;
         var12[2] = var9.getString("RELATION_NAME").trim();
         var12[3] = var9.getString("FIELD_NAME").trim();
         short var13 = var9.getShort("FIELD_TYPE");
         short var14 = var9.getShort("FIELD_SUB_TYPE");
         short var15 = var9.getShort("FIELD_SCALE");
         int var16 = interbase.interclient.IBTypes.getDataType(var13, var14, var15);
         var12[4] = new Short((short)var16);
         int var17 = interbase.interclient.IBTypes.getIBType(var13, var14, var15);
         var12[5] = interbase.interclient.IBTypes.getIBTypeName(var17);
         short var18 = var9.getShort("FIELD_LENGTH");
         var12[6] = new Integer(interbase.interclient.IBTypes.getColumnSize(var17, var18));
         var12[7] = new Integer(0);
         var12[8] = new Integer(Math.abs(var15));
         var12[9] = new Integer(interbase.interclient.IBTypes.getRadix(var16));
         short var19 = var9.getShort("NULL_FLAG");
         var12[10] = var19 == 1 ? new Integer(0) : new Integer(1);
         var12[11] = null;
         var12[12] = null;
         var12[13] = null;
         var12[14] = null;
         var12[15] = new Integer(var18);
         var12[16] = new Integer(var9.getShort("FIELD_POSITION") + 1);
         var12[17] = var19 == 1 ? "NO" : "YES";
         var11.add(var12);
      }

      this.setCannedResult(var10, var11, 18);
      var9.close();
      this.pstmt_.close();
      return this.rs_;
   }

   public synchronized java.sql.ResultSet getColumnPrivileges(String var1, String var2, String var3, String var4) throws SQLException {
      this.checkForClosedConnection();
      this.systemTableQueryPreamble(var1, var2);
      SqlPattern var5 = new SqlPattern("RF.RDB$FIELD_NAME", var4);
      String var6 = "select RF.RDB$RELATION_NAME as TABLE_NAME, RF.RDB$FIELD_NAME as COLUMN_NAME, UP.RDB$GRANTOR as GRANTOR, UP.RDB$USER as GRANTEE, UP.RDB$PRIVILEGE as PRIVILEGE, UP.RDB$GRANT_OPTION as IS_GRANTABLE from RDB$RELATION_FIELDS RF, RDB$FIELDS F, RDB$USER_PRIVILEGES UP where RF.RDB$RELATION_NAME = UP.RDB$RELATION_NAME and RF.RDB$FIELD_SOURCE = F.RDB$FIELD_NAME  and (UP.RDB$FIELD_NAME is null or UP.RDB$FIELD_NAME = RF.RDB$FIELD_NAME) and UP.RDB$RELATION_NAME = ? and ((" + var5.getClause() + " UP.RDB$OBJECT_TYPE = 0) or (RF.RDB$FIELD_NAME is null and UP.RDB$OBJECT_TYPE = 0)) order by 3 ";
      this.pstmt_ = (interbase.interclient.PreparedStatement)this.connection_.prepareStatement(3, var6);
      if (var3 == null || "".equals(var3)) {
         var3 = new String("%");
      }

      this.pstmt_.setString(1, var3);
      if (!"%".equals(var4) && !"".equals(var4)) {
         this.pstmt_.setString(2, var5.getPattern());
      }

      interbase.interclient.ResultSet var7 = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
      interbase.interclient.XSQLVAR[] var8 = new interbase.interclient.XSQLVAR[8];
      var8[0] = new interbase.interclient.XSQLVAR();
      var8[0].sqltype = 601;
      var8[0].sqllen = 68;
      var8[0].sqlind = -1;
      var8[0].sqlname = "TABLE_CAT";
      var8[0].relname = "RDB$COLUMNPRIV";
      var8[1] = new interbase.interclient.XSQLVAR();
      var8[1].sqltype = 601;
      var8[1].sqllen = 68;
      var8[1].sqlind = -1;
      var8[1].sqlname = "TABLE_SCHEM";
      var8[1].relname = "RDB$COLUMNPRIV";
      var8[2] = new interbase.interclient.XSQLVAR();
      var8[2].sqltype = 600;
      var8[2].sqllen = 68;
      var8[2].sqlind = 0;
      var8[2].sqlname = "TABLE_NAME";
      var8[2].relname = "RDB$COLUMNPRIV";
      var8[3] = new interbase.interclient.XSQLVAR();
      var8[3].sqltype = 600;
      var8[3].sqllen = 68;
      var8[3].sqlind = 0;
      var8[3].sqlname = "COLUMN_NAME";
      var8[3].relname = "RDB$COLUMNPRIV";
      var8[4] = new interbase.interclient.XSQLVAR();
      var8[4].sqltype = 601;
      var8[4].sqllen = 68;
      var8[4].sqlind = 0;
      var8[4].sqlname = "GRANTOR";
      var8[4].relname = "RDB$COLUMNPRIV";
      var8[5] = new interbase.interclient.XSQLVAR();
      var8[5].sqltype = 600;
      var8[5].sqllen = 68;
      var8[5].sqlind = 0;
      var8[5].sqlname = "GRANTEE";
      var8[5].relname = "RDB$COLUMNPRIV";
      var8[6] = new interbase.interclient.XSQLVAR();
      var8[6].sqltype = 600;
      var8[6].sqllen = 68;
      var8[6].sqlind = 0;
      var8[6].sqlname = "PRIVILEGE";
      var8[6].relname = "RDB$COLUMNPRIV";
      var8[7] = new interbase.interclient.XSQLVAR();
      var8[7].sqltype = 601;
      var8[7].sqllen = 68;
      var8[7].sqlind = 0;
      var8[7].sqlname = "IS_GRANTABLE";
      var8[7].relname = "RDB$COLUMNPRIV";
      ArrayList var9 = new ArrayList();

      while(var7.next()) {
         Object[] var10 = new Object[]{null, null, var7.getString("TABLE_NAME").trim(), var7.getString("COLUMN_NAME").trim(), var7.getString("GRANTOR").trim(), var7.getString("GRANTEE").trim(), this.mapPrivilegeLiterals(var7.getString("PRIVILEGE").trim()), new String(var7.getShort("IS_GRANTABLE") == 1 ? "YES" : "NO")};
         var9.add(var10);
      }

      this.setCannedResult(var8, var9, 8);
      var7.close();
      this.pstmt_.close();
      return this.rs_;
   }

   private String mapPrivilegeLiterals(String var1) throws SQLException {
      if ("I".equals(var1)) {
         return new String("INSERT");
      } else if ("S".equals(var1)) {
         return new String("SELECT");
      } else if ("U".equals(var1)) {
         return new String("UPDATE");
      } else if ("D".equals(var1)) {
         return new String("DELETE");
      } else {
         return "R".equals(var1) ? new String("REFERENCES") : null;
      }
   }

   public synchronized java.sql.ResultSet getTablePrivileges(String var1, String var2, String var3) throws SQLException {
      this.checkForClosedConnection();
      this.systemTableQueryPreamble(var1, var2);
      SqlPattern var4 = new SqlPattern("RDB$RELATION_NAME", var3);
      String var5 = "select null as TABLE_CAT,  null as TABLE_SCHEM, RDB$RELATION_NAME as TABLE_NAME, RDB$GRANTOR as GRANTOR,  RDB$USER as GRANTEE,  RDB$PRIVILEGE as PRIVILEGE,  RDB$GRANT_OPTION as IS_GRANTABLE from RDB$USER_PRIVILEGES where " + var4.getClause() + " RDB$OBJECT_TYPE = 0 and RDB$FIELD_NAME is null order by 1, 4";
      this.pstmt_ = (interbase.interclient.PreparedStatement)this.connection_.prepareStatement(3, var5);
      if (!"%".equals(var3) && !"".equals(var3)) {
         this.pstmt_.setString(1, var4.getPattern());
      }

      interbase.interclient.ResultSet var6 = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
      interbase.interclient.XSQLVAR[] var7 = new interbase.interclient.XSQLVAR[7];
      var7[0] = new interbase.interclient.XSQLVAR();
      var7[0].sqltype = 601;
      var7[0].sqllen = 68;
      var7[0].sqlind = -1;
      var7[0].sqlname = "TABLE_CAT";
      var7[0].relname = "RDB$COLUMNPRIV";
      var7[1] = new interbase.interclient.XSQLVAR();
      var7[1].sqltype = 601;
      var7[1].sqllen = 68;
      var7[1].sqlind = -1;
      var7[1].sqlname = "TABLE_SCHEM";
      var7[1].relname = "RDB$COLUMNPRIV";
      var7[2] = new interbase.interclient.XSQLVAR();
      var7[2].sqltype = 600;
      var7[2].sqllen = 68;
      var7[2].sqlind = 0;
      var7[2].sqlname = "TABLE_NAME";
      var7[2].relname = "RDB$COLUMNPRIV";
      var7[3] = new interbase.interclient.XSQLVAR();
      var7[3].sqltype = 601;
      var7[3].sqllen = 68;
      var7[3].sqlind = 0;
      var7[3].sqlname = "GRANTOR";
      var7[3].relname = "RDB$COLUMNPRIV";
      var7[4] = new interbase.interclient.XSQLVAR();
      var7[4].sqltype = 600;
      var7[4].sqllen = 68;
      var7[4].sqlind = 0;
      var7[4].sqlname = "GRANTEE";
      var7[4].relname = "RDB$COLUMNPRIV";
      var7[5] = new interbase.interclient.XSQLVAR();
      var7[5].sqltype = 600;
      var7[5].sqllen = 68;
      var7[5].sqlind = 0;
      var7[5].sqlname = "PRIVILEGE";
      var7[5].relname = "RDB$COLUMNPRIV";
      var7[6] = new interbase.interclient.XSQLVAR();
      var7[6].sqltype = 600;
      var7[6].sqllen = 68;
      var7[6].sqlind = 0;
      var7[6].sqlname = "IS_GRANTABLE";
      var7[6].relname = "RDB$COLUMNPRIV";
      ArrayList var8 = new ArrayList();

      while(var6.next()) {
         Object[] var9 = new Object[]{null, null, var6.getString("TABLE_NAME").trim(), var6.getString("GRANTOR").trim(), var6.getString("GRANTEE").trim(), this.mapPrivilegeLiterals(var6.getString("PRIVILEGE").trim()), new String(var6.getShort("IS_GRANTABLE") == 1 ? "YES" : "NO")};
         var8.add(var9);
      }

      this.setCannedResult(var7, var8, 7);
      var6.close();
      this.pstmt_.close();
      return this.rs_;
   }

   public synchronized java.sql.ResultSet getBestRowIdentifier(String var1, String var2, String var3, int var4, boolean var5) throws SQLException {
      this.checkForClosedConnection();
      this.systemTableQueryPreamble(var1, var2);
      String var6 = var3.toUpperCase();
      this.pstmt_ = (interbase.interclient.PreparedStatement)this.connection_.prepareStatement(3, "select RF.RDB$FIELD_NAME, F.RDB$FIELD_TYPE, F.RDB$FIELD_SUB_TYPE, F.RDB$FIELD_SCALE, F.RDB$FIELD_LENGTH  from RDB$RELATION_CONSTRAINTS RC, RDB$INDEX_SEGMENTS ISGMT, RDB$RELATION_FIELDS RF,RDB$FIELDS F where RC.RDB$RELATION_NAME = ? and RC.RDB$CONSTRAINT_TYPE = 'PRIMARY KEY' and ISGMT.RDB$INDEX_NAME = RC.RDB$INDEX_NAME and RF.RDB$FIELD_NAME = ISGMT.RDB$FIELD_NAME and RF.RDB$RELATION_NAME = RC.RDB$RELATION_NAME and F.RDB$FIELD_NAME = RF.RDB$FIELD_SOURCE union all select RF.RDB$FIELD_NAME,F.RDB$FIELD_TYPE,F.RDB$FIELD_SUB_TYPE, F.RDB$FIELD_SCALE,F.RDB$FIELD_LENGTH from RDB$INDICES IND,RDB$INDEX_SEGMENTS ISGMT, RDB$RELATION_FIELDS RF,RDB$FIELDS F where not exists  (select * from RDB$RELATION_CONSTRAINTS where RDB$RELATION_NAME = ? and RDB$CONSTRAINT_TYPE = 'PRIMARY KEY') and IND.RDB$INDEX_NAME in (select max(RDB$INDEX_NAME) from RDB$INDICES where RDB$RELATION_NAME = ? and RDB$UNIQUE_FLAG   = 1) and ISGMT.RDB$INDEX_NAME = IND.RDB$INDEX_NAME and RF.RDB$FIELD_NAME = ISGMT.RDB$FIELD_NAME and RF.RDB$RELATION_NAME =  IND.RDB$RELATION_NAME and  F.RDB$FIELD_NAME = RF.RDB$FIELD_SOURCE order by 1");

      for(int var7 = 0; var7 < 3; ++var7) {
         this.pstmt_.setString(var7 + 1, var6);
      }

      interbase.interclient.ResultSet var8 = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
      interbase.interclient.XSQLVAR[] var9 = new interbase.interclient.XSQLVAR[8];
      var9[0] = new interbase.interclient.XSQLVAR();
      var9[0].sqltype = 500;
      var9[0].sqlname = "SCOPE";
      var9[0].relname = "RDB$BEST_ROW";
      var9[1] = new interbase.interclient.XSQLVAR();
      var9[1].sqltype = 600;
      var9[1].sqllen = 68;
      var9[1].sqlind = 0;
      var9[1].sqlname = "COLUMN_NAME";
      var9[1].relname = "RDB$BEST_ROW";
      var9[2] = new interbase.interclient.XSQLVAR();
      var9[2].sqltype = 500;
      var9[2].sqlname = "DATA_TYPE";
      var9[2].relname = "RDB$BEST_ROW";
      var9[3] = new interbase.interclient.XSQLVAR();
      var9[3].sqltype = 600;
      var9[3].sqllen = 68;
      var9[3].sqlind = 0;
      var9[3].sqlname = "TYPE_NAME";
      var9[3].relname = "RDB$BEST_ROW";
      var9[4] = new interbase.interclient.XSQLVAR();
      var9[4].sqltype = 496;
      var9[4].sqlname = "COLUMN_SIZE";
      var9[4].relname = "RDB$BEST_ROW";
      var9[5] = new interbase.interclient.XSQLVAR();
      var9[5].sqltype = 496;
      var9[5].sqlname = "BUFFER_LENGTH";
      var9[5].relname = "RDB$BEST_ROW";
      var9[6] = new interbase.interclient.XSQLVAR();
      var9[6].sqltype = 500;
      var9[6].sqlname = "DECIMAL_DIGITS";
      var9[6].relname = "RDB$BEST_ROW";
      var9[7] = new interbase.interclient.XSQLVAR();
      var9[7].sqltype = 500;
      var9[7].sqlname = "PSEUDO_COLUMN";
      var9[7].relname = "RDB$BEST_ROW";
      ArrayList var10 = new ArrayList();

      while(var8.next()) {
         Object[] var11 = new Object[8];
         var11[0] = new Short((short)2);
         var11[1] = var8.getString(1).trim();
         int var12 = interbase.interclient.IBTypes.getIBType(var8.getShort(2), var8.getShort(3), var8.getShort(4));
         var11[2] = new Short((short) interbase.interclient.IBTypes.getSQLType(var12));
         var11[3] = interbase.interclient.IBTypes.getIBTypeName(var12);
         var11[4] = new Integer(interbase.interclient.IBTypes.getColumnSize(var12, var8.getInt(5)));
         var11[5] = var11[4];
         var11[6] = new Short((short)Math.abs(var8.getInt(4)));
         var11[7] = new Short((short)0);
         var10.add(var11);
      }

      this.setCannedResult(var9, var10, 8);
      var8.close();
      this.pstmt_.close();
      return this.rs_;
   }

   public synchronized java.sql.ResultSet getVersionColumns(String var1, String var2, String var3) throws SQLException {
      this.checkForClosedConnection();
      this.systemTableQueryPreamble(var1, var2);
      String var4 = var3.toUpperCase();
      this.pstmt_ = (interbase.interclient.PreparedStatement)this.connection_.prepareStatement(3, "select RF.RDB$FIELD_NAME, F.RDB$FIELD_TYPE, F.RDB$FIELD_SUB_TYPE, F.RDB$FIELD_SCALE, F.RDB$FIELD_LENGTH, F.RDB$CHARACTER_LENGTH  from  RDB$FIELDS F, RDB$RELATION_FIELDS RF  where 0 = 1 and  RF.RDB$RELATION_NAME = ? and RF.RDB$FIELD_SOURCE = F.RDB$FIELD_NAME and F.RDB$COMPUTED_BLR IS NOT NULL");
      this.pstmt_.setString(1, var4);
      interbase.interclient.ResultSet var5 = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
      interbase.interclient.XSQLVAR[] var6 = new interbase.interclient.XSQLVAR[8];
      var6[0] = new interbase.interclient.XSQLVAR();
      var6[0].sqltype = 500;
      var6[0].sqlname = "SCOPE";
      var6[0].relname = "RDB$VERSIONCOL";
      var6[1] = new interbase.interclient.XSQLVAR();
      var6[1].sqltype = 600;
      var6[1].sqllen = 68;
      var6[1].sqlind = 0;
      var6[1].sqlname = "COLUMN_NAME";
      var6[1].relname = "RDB$VERSIONCOL";
      var6[2] = new interbase.interclient.XSQLVAR();
      var6[2].sqltype = 500;
      var6[2].sqlname = "DATA_TYPE";
      var6[2].relname = "RDB$VERSIONCOL";
      var6[3] = new interbase.interclient.XSQLVAR();
      var6[3].sqltype = 600;
      var6[3].sqllen = 68;
      var6[3].sqlind = -1;
      var6[3].sqlname = "TYPE_NAME";
      var6[3].relname = "RDB$VERSIONCOL";
      var6[4] = new interbase.interclient.XSQLVAR();
      var6[4].sqltype = 496;
      var6[4].sqlname = "COLUMN_SIZE";
      var6[4].relname = "RDB$VERSIONCOL";
      var6[5] = new interbase.interclient.XSQLVAR();
      var6[5].sqltype = 496;
      var6[5].sqlname = "BUFFER_LENGTH";
      var6[5].relname = "RDB$VERSIONCOL";
      var6[6] = new interbase.interclient.XSQLVAR();
      var6[6].sqltype = 500;
      var6[6].sqlname = "DECIMAL_DIGITS";
      var6[6].relname = "RDB$VERSIONCOL";
      var6[7] = new interbase.interclient.XSQLVAR();
      var6[7].sqltype = 500;
      var6[7].sqlname = "PSEUDO_COLUMN";
      var6[7].relname = "RDB$VERSIONCOL";
      ArrayList var7 = new ArrayList();

      while(var5.next()) {
         Object[] var8 = new Object[8];
         var8[0] = new Short((short)0);
         var8[1] = var5.getString(1).trim();
         int var9 = interbase.interclient.IBTypes.getIBType(var5.getShort(2), var5.getShort(3), var5.getShort(4));
         var8[2] = new Short((short) interbase.interclient.IBTypes.getSQLType(var9));
         var8[3] = interbase.interclient.IBTypes.getIBTypeName(var9);
         var8[4] = new Integer(interbase.interclient.IBTypes.getColumnSize(var9, var5.getInt(5)));
         var8[5] = var8[4];
         var8[6] = new Short((short)Math.abs(var5.getInt(4)));
         var8[7] = new Short((short)0);
         var7.add(var8);
      }

      this.setCannedResult(var6, var7, 8);
      var5.close();
      this.pstmt_.close();
      return this.rs_;
   }

   public synchronized java.sql.ResultSet getPrimaryKeys(String var1, String var2, String var3) throws SQLException {
      this.checkForClosedConnection();
      this.systemTableQueryPreamble(var1, var2);
      SqlPattern var4 = new SqlPattern("RC.RDB$RELATION_NAME", var3);
      String var5 = "select null as TABLE_CAT, null as TABLE_SCHEM, RC.RDB$RELATION_NAME as TABLE_NAME, ISGMT.RDB$FIELD_NAME as COLUMN_NAME, CAST ((ISGMT.RDB$FIELD_POSITION + 1) as SMALLINT) as KEY_SEQ, RC.RDB$CONSTRAINT_NAME as PK_NAME from RDB$RELATION_CONSTRAINTS RC, RDB$INDEX_SEGMENTS ISGMT where " + var4.getClause() + "RC.RDB$INDEX_NAME = ISGMT.RDB$INDEX_NAME and RC.RDB$CONSTRAINT_TYPE = 'PRIMARY KEY' order by ISGMT.RDB$FIELD_NAME ";
      this.pstmt_ = (interbase.interclient.PreparedStatement)this.connection_.prepareStatement(3, var5);
      if (!"%".equals(var3) && !"".equals(var3)) {
         this.pstmt_.setString(1, var4.getPattern());
      }

      interbase.interclient.ResultSet var6 = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
      interbase.interclient.XSQLVAR[] var7 = new interbase.interclient.XSQLVAR[6];
      var7[0] = new interbase.interclient.XSQLVAR();
      var7[0].sqltype = 601;
      var7[0].sqllen = 68;
      var7[0].sqlind = -1;
      var7[0].sqlname = "TABLE_CAT";
      var7[0].relname = "RDB$COLUMNINFO";
      var7[1] = new interbase.interclient.XSQLVAR();
      var7[1].sqltype = 601;
      var7[1].sqllen = 68;
      var7[1].sqlind = -1;
      var7[1].sqlname = "TABLE_SCHEM";
      var7[1].relname = "RDB$COLUMNINFO";
      var7[2] = new interbase.interclient.XSQLVAR();
      var7[2].sqltype = 600;
      var7[2].sqllen = 68;
      var7[2].sqlind = 0;
      var7[2].sqlname = "TABLE_NAME";
      var7[2].relname = "RDB$COLUMNINFO";
      var7[3] = new interbase.interclient.XSQLVAR();
      var7[3].sqltype = 600;
      var7[3].sqllen = 68;
      var7[3].sqlind = 0;
      var7[3].sqlname = "COLUMN_NAME";
      var7[3].relname = "RDB$COLUMNINFO";
      var7[4] = new interbase.interclient.XSQLVAR();
      var7[4].sqltype = 500;
      var7[4].sqlname = "KEY_SEQ";
      var7[4].relname = "RDB$COLUMNINFO";
      var7[5] = new interbase.interclient.XSQLVAR();
      var7[5].sqltype = 601;
      var7[5].sqllen = 68;
      var7[5].sqlind = 0;
      var7[5].sqlname = "PK_NAME";
      var7[5].relname = "RDB$COLUMNINFO";
      ArrayList var8 = new ArrayList();

      while(var6.next()) {
         Object[] var9 = new Object[]{null, null, var6.getString("TABLE_NAME").trim(), var6.getString("COLUMN_NAME").trim(), new Short(var6.getShort("KEY_SEQ")), var6.getString("PK_NAME").trim()};
         var8.add(var9);
      }

      this.setCannedResult(var7, var8, 6);
      var6.close();
      this.pstmt_.close();
      return this.rs_;
   }

   public synchronized java.sql.ResultSet getImportedKeys(String var1, String var2, String var3) throws SQLException {
      this.checkForClosedConnection();
      this.systemTableQueryPreamble(var1, var2);
      SqlPattern var4 = new SqlPattern("FK.RDB$RELATION_NAME", var3);
      String var5 = "select PK.RDB$RELATION_NAME as PKTABLE_NAME  ,ISP.RDB$FIELD_NAME as PKCOLUMN_NAME  ,FK.RDB$RELATION_NAME as FKTABLE_NAME  ,ISF.RDB$FIELD_NAME as FKCOLUMN_NAME  ,CAST ((ISP.RDB$FIELD_POSITION + 1) as SMALLINT) as KEY_SEQ  ,PRC.RDB$UPDATE_RULE as UPDATE_RULE  ,PRC.RDB$DELETE_RULE as DELETE_RULE  ,PK.RDB$CONSTRAINT_NAME as PK_NAME  ,FK.RDB$CONSTRAINT_NAME as FK_NAME  from  RDB$RELATION_CONSTRAINTS PK  ,RDB$RELATION_CONSTRAINTS FK  ,RDB$REF_CONSTRAINTS FRC  ,RDB$REF_CONSTRAINTS PRC  ,RDB$INDEX_SEGMENTS ISP  ,RDB$INDEX_SEGMENTS ISF  WHERE " + var4.getClause() + " FK.RDB$CONSTRAINT_TYPE = 'FOREIGN KEY' and FK.RDB$CONSTRAINT_NAME = FRC.RDB$CONSTRAINT_NAME and FRC.RDB$CONST_NAME_UQ = PK.RDB$CONSTRAINT_NAME and PK.RDB$CONSTRAINT_TYPE = 'PRIMARY KEY' and PK.RDB$INDEX_NAME = ISP.RDB$INDEX_NAME and ISF.RDB$INDEX_NAME = FK.RDB$INDEX_NAME   and ISP.RDB$FIELD_POSITION = ISF.RDB$FIELD_POSITION  and PRC.RDB$CONSTRAINT_NAME = FK.RDB$CONSTRAINT_NAME  order by 1,5";
      this.pstmt_ = (interbase.interclient.PreparedStatement)this.connection_.prepareStatement(3, var5);
      if (!"%".equals(var3) && !"".equals(var3)) {
         this.pstmt_.setString(1, var4.getPattern());
      }

      interbase.interclient.ResultSet var6 = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
      interbase.interclient.XSQLVAR[] var7 = new interbase.interclient.XSQLVAR[14];
      var7[0] = new interbase.interclient.XSQLVAR();
      var7[0].sqltype = 601;
      var7[0].sqllen = 68;
      var7[0].sqlind = -1;
      var7[0].sqlname = "PKTABLE_CAT";
      var7[0].relname = "COLUMNINFO";
      var7[1] = new interbase.interclient.XSQLVAR();
      var7[1].sqltype = 601;
      var7[1].sqllen = 68;
      var7[1].sqlind = -1;
      var7[1].sqlname = "PKTABLE_SCHEM";
      var7[1].relname = "COLUMNINFO";
      var7[2] = new interbase.interclient.XSQLVAR();
      var7[2].sqltype = 600;
      var7[2].sqllen = 68;
      var7[2].sqlind = 0;
      var7[2].sqlname = "PKTABLE_NAME";
      var7[2].relname = "COLUMNINFO";
      var7[3] = new interbase.interclient.XSQLVAR();
      var7[3].sqltype = 600;
      var7[3].sqllen = 68;
      var7[3].sqlind = 0;
      var7[3].sqlname = "PKCOLUMN_NAME";
      var7[3].relname = "COLUMNINFO";
      var7[4] = new interbase.interclient.XSQLVAR();
      var7[4].sqltype = 601;
      var7[4].sqllen = 68;
      var7[4].sqlind = -1;
      var7[4].sqlname = "FKTABLE_CAT";
      var7[4].relname = "COLUMNINFO";
      var7[5] = new interbase.interclient.XSQLVAR();
      var7[5].sqltype = 601;
      var7[5].sqllen = 68;
      var7[5].sqlind = -1;
      var7[5].sqlname = "FKTABLE_SCHEM";
      var7[5].relname = "COLUMNINFO";
      var7[6] = new interbase.interclient.XSQLVAR();
      var7[6].sqltype = 600;
      var7[6].sqllen = 68;
      var7[6].sqlind = 0;
      var7[6].sqlname = "FKTABLE_NAME";
      var7[6].relname = "COLUMNINFO";
      var7[7] = new interbase.interclient.XSQLVAR();
      var7[7].sqltype = 600;
      var7[7].sqllen = 68;
      var7[7].sqlind = 0;
      var7[7].sqlname = "FKCOLUMN_NAME";
      var7[7].relname = "COLUMNINFO";
      var7[8] = new interbase.interclient.XSQLVAR();
      var7[8].sqltype = 500;
      var7[8].sqlname = "KEY_SEQ";
      var7[8].relname = "COLUMNINFO";
      var7[9] = new interbase.interclient.XSQLVAR();
      var7[9].sqltype = 500;
      var7[9].sqlname = "UPDATE_RULE";
      var7[9].relname = "COLUMNINFO";
      var7[10] = new interbase.interclient.XSQLVAR();
      var7[10].sqltype = 500;
      var7[10].sqlname = "DELETE_RULE";
      var7[10].relname = "COLUMNINFO";
      var7[11] = new interbase.interclient.XSQLVAR();
      var7[11].sqltype = 601;
      var7[11].sqllen = 68;
      var7[11].sqlind = 0;
      var7[11].sqlname = "FK_NAME";
      var7[11].relname = "COLUMNINFO";
      var7[12] = new interbase.interclient.XSQLVAR();
      var7[12].sqltype = 601;
      var7[12].sqllen = 68;
      var7[12].sqlind = 0;
      var7[12].sqlname = "PK_NAME";
      var7[12].relname = "COLUMNINFO";
      var7[13] = new interbase.interclient.XSQLVAR();
      var7[13].sqltype = 500;
      var7[13].sqlname = "DEFERRABILITY";
      var7[13].relname = "COLUMNINFO";
      ArrayList var8 = new ArrayList();

      while(var6.next()) {
         Object[] var9 = new Object[]{null, null, var6.getString("PKTABLE_NAME").trim(), var6.getString("PKCOLUMN_NAME").trim(), null, null, var6.getString("FKTABLE_NAME").trim(), var6.getString("FKCOLUMN_NAME").trim(), new Short(var6.getShort("KEY_SEQ")), new Short(this.mapRefConstraints(var6.getString("UPDATE_RULE"))), new Short(this.mapRefConstraints(var6.getString("DELETE_RULE"))), var6.getString("FK_NAME").trim(), var6.getString("PK_NAME").trim(), new Short((short)0)};
         var8.add(var9);
      }

      this.setCannedResult(var7, var8, 14);
      var6.close();
      this.pstmt_.close();
      return this.rs_;
   }

   private short mapRefConstraints(String var1) {
      if ("RESTRICT".equalsIgnoreCase(var1)) {
         return 1;
      } else if ("CASCADE".equalsIgnoreCase(var1)) {
         return 0;
      } else if ("SET NULL".equalsIgnoreCase(var1)) {
         return 2;
      } else if ("NO ACTION".equalsIgnoreCase(var1)) {
         return 3;
      } else {
         return (short)("SET DEFAULT".equalsIgnoreCase(var1) ? 4 : 1);
      }
   }

   public synchronized java.sql.ResultSet getExportedKeys(String var1, String var2, String var3) throws SQLException {
      this.checkForClosedConnection();
      this.systemTableQueryPreamble(var1, var2);
      SqlPattern var4 = new SqlPattern("PK.RDB$RELATION_NAME", var3);
      String var5 = "select PK.RDB$RELATION_NAME as PKTABLE_NAME  ,ISP.RDB$FIELD_NAME as PKCOLUMN_NAME  ,FK.RDB$RELATION_NAME as FKTABLE_NAME  ,ISF.RDB$FIELD_NAME as FKCOLUMN_NAME  ,CAST ((ISP.RDB$FIELD_POSITION + 1) as SMALLINT) as KEY_SEQ  ,FRC.RDB$UPDATE_RULE as UPDATE_RULE  ,FRC.RDB$DELETE_RULE as DELETE_RULE  ,PK.RDB$CONSTRAINT_NAME as PK_NAME  ,FK.RDB$CONSTRAINT_NAME as FK_NAME  from  RDB$RELATION_CONSTRAINTS PK  ,RDB$RELATION_CONSTRAINTS FK  ,RDB$REF_CONSTRAINTS FRC  ,RDB$REF_CONSTRAINTS PRC  ,RDB$INDEX_SEGMENTS ISP  ,RDB$INDEX_SEGMENTS ISF  WHERE " + var4.getClause() + " PK.RDB$CONSTRAINT_TYPE = 'PRIMARY KEY' and FRC.RDB$CONST_NAME_UQ = PK.RDB$CONSTRAINT_NAME and  FK.RDB$CONSTRAINT_NAME = FRC.RDB$CONSTRAINT_NAME and FK.RDB$CONSTRAINT_TYPE = 'FOREIGN KEY' and PK.RDB$INDEX_NAME = ISP.RDB$INDEX_NAME and ISF.RDB$INDEX_NAME = FK.RDB$INDEX_NAME   and ISP.RDB$FIELD_POSITION = ISF.RDB$FIELD_POSITION  and PRC.RDB$CONSTRAINT_NAME = FK.RDB$CONSTRAINT_NAME order by 3,5";
      this.pstmt_ = (interbase.interclient.PreparedStatement)this.connection_.prepareStatement(3, var5);
      if (!"%".equals(var3) && !"".equals(var3)) {
         this.pstmt_.setString(1, var4.getPattern());
      }

      interbase.interclient.ResultSet var6 = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
      interbase.interclient.XSQLVAR[] var7 = new interbase.interclient.XSQLVAR[14];
      var7[0] = new interbase.interclient.XSQLVAR();
      var7[0].sqltype = 601;
      var7[0].sqllen = 68;
      var7[0].sqlind = -1;
      var7[0].sqlname = "PKTABLE_CAT";
      var7[0].relname = "COLUMNINFO";
      var7[1] = new interbase.interclient.XSQLVAR();
      var7[1].sqltype = 601;
      var7[1].sqllen = 68;
      var7[1].sqlind = -1;
      var7[1].sqlname = "PKTABLE_SCHEM";
      var7[1].relname = "COLUMNINFO";
      var7[2] = new interbase.interclient.XSQLVAR();
      var7[2].sqltype = 600;
      var7[2].sqllen = 68;
      var7[2].sqlind = 0;
      var7[2].sqlname = "PKTABLE_NAME";
      var7[2].relname = "COLUMNINFO";
      var7[3] = new interbase.interclient.XSQLVAR();
      var7[3].sqltype = 600;
      var7[3].sqllen = 68;
      var7[3].sqlind = 0;
      var7[3].sqlname = "PKCOLUMN_NAME";
      var7[3].relname = "COLUMNINFO";
      var7[4] = new interbase.interclient.XSQLVAR();
      var7[4].sqltype = 601;
      var7[4].sqllen = 68;
      var7[4].sqlind = -1;
      var7[4].sqlname = "FKTABLE_CAT";
      var7[4].relname = "COLUMNINFO";
      var7[5] = new interbase.interclient.XSQLVAR();
      var7[5].sqltype = 601;
      var7[5].sqllen = 68;
      var7[5].sqlind = -1;
      var7[5].sqlname = "FKTABLE_SCHEM";
      var7[5].relname = "COLUMNINFO";
      var7[6] = new interbase.interclient.XSQLVAR();
      var7[6].sqltype = 600;
      var7[6].sqllen = 68;
      var7[6].sqlind = 0;
      var7[6].sqlname = "FKTABLE_NAME";
      var7[6].relname = "COLUMNINFO";
      var7[7] = new interbase.interclient.XSQLVAR();
      var7[7].sqltype = 600;
      var7[7].sqllen = 68;
      var7[7].sqlind = 0;
      var7[7].sqlname = "FKCOLUMN_NAME";
      var7[7].relname = "COLUMNINFO";
      var7[8] = new interbase.interclient.XSQLVAR();
      var7[8].sqltype = 500;
      var7[8].sqlname = "KEY_SEQ";
      var7[8].relname = "COLUMNINFO";
      var7[9] = new interbase.interclient.XSQLVAR();
      var7[9].sqltype = 500;
      var7[9].sqlname = "UPDATE_RULE";
      var7[9].relname = "COLUMNINFO";
      var7[10] = new interbase.interclient.XSQLVAR();
      var7[10].sqltype = 500;
      var7[10].sqlname = "DELETE_RULE";
      var7[10].relname = "COLUMNINFO";
      var7[11] = new interbase.interclient.XSQLVAR();
      var7[11].sqltype = 601;
      var7[11].sqllen = 68;
      var7[11].sqlind = 0;
      var7[11].sqlname = "FK_NAME";
      var7[11].relname = "COLUMNINFO";
      var7[12] = new interbase.interclient.XSQLVAR();
      var7[12].sqltype = 601;
      var7[12].sqllen = 68;
      var7[12].sqlind = 0;
      var7[12].sqlname = "PK_NAME";
      var7[12].relname = "COLUMNINFO";
      var7[13] = new interbase.interclient.XSQLVAR();
      var7[13].sqltype = 500;
      var7[13].sqlname = "DEFERRABILITY";
      var7[13].relname = "COLUMNINFO";
      ArrayList var8 = new ArrayList();

      while(var6.next()) {
         Object[] var9 = new Object[]{null, null, var6.getString("PKTABLE_NAME").trim(), var6.getString("PKCOLUMN_NAME").trim(), null, null, var6.getString("FKTABLE_NAME").trim(), var6.getString("FKCOLUMN_NAME").trim(), new Short(var6.getShort("KEY_SEQ")), new Short(this.mapRefConstraints(var6.getString("UPDATE_RULE"))), new Short(this.mapRefConstraints(var6.getString("DELETE_RULE"))), var6.getString("FK_NAME"), var6.getString("PK_NAME"), new Short((short)0)};
         var8.add(var9);
      }

      this.setCannedResult(var7, var8, 14);
      var6.close();
      this.pstmt_.close();
      return this.rs_;
   }

   public synchronized java.sql.ResultSet getCrossReference(String var1, String var2, String var3, String var4, String var5, String var6) throws SQLException {
      this.checkForClosedConnection();
      this.systemTableQueryPreamble(var1, var2);
      this.systemTableQueryPreamble(var4, var5);
      SqlPattern var7 = new SqlPattern("PK.RDB$RELATION_NAME", var3);
      SqlPattern var8 = new SqlPattern("FK.RDB$RELATION_NAME", var6);
      String var9 = "select PK.RDB$RELATION_NAME as PKTABLE_NAME  ,ISP.RDB$FIELD_NAME as PKCOLUMN_NAME , FK.RDB$RELATION_NAME as FKTABLE_NAME  ,ISF.RDB$FIELD_NAME as FKCOLUMN_NAME  ,CAST ((ISP.RDB$FIELD_POSITION + 1) as SMALLINT) as KEY_SEQ  ,RC.RDB$UPDATE_RULE as UPDATE_RULE  ,RC.RDB$DELETE_RULE as DELETE_RULE  ,PK.RDB$CONSTRAINT_NAME as PK_NAME  ,FK.RDB$CONSTRAINT_NAME as FK_NAME  from  RDB$RELATION_CONSTRAINTS PK  ,RDB$RELATION_CONSTRAINTS FK  ,RDB$REF_CONSTRAINTS RC  ,RDB$INDEX_SEGMENTS ISP  ,RDB$INDEX_SEGMENTS ISF  WHERE " + var7.getClause() + var8.getClause() + " FK.RDB$CONSTRAINT_NAME = RC.RDB$CONSTRAINT_NAME  and PK.RDB$CONSTRAINT_NAME = RC.RDB$CONST_NAME_UQ  and ISP.RDB$INDEX_NAME = PK.RDB$INDEX_NAME  and ISF.RDB$INDEX_NAME = FK.RDB$INDEX_NAME  and ISP.RDB$FIELD_POSITION = ISF.RDB$FIELD_POSITION order by 3,5";
      this.pstmt_ = (interbase.interclient.PreparedStatement)this.connection_.prepareStatement(3, var9);
      int var10 = 1;
      if (!"%".equals(var3) && !"".equals(var3)) {
         this.pstmt_.setString(var10, var7.getPattern());
         ++var10;
      }

      if (!"%".equals(var6) && !"".equals(var6)) {
         this.pstmt_.setString(var10, var8.getPattern());
      }

      interbase.interclient.ResultSet var11 = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
      interbase.interclient.XSQLVAR[] var12 = new interbase.interclient.XSQLVAR[14];
      var12[0] = new interbase.interclient.XSQLVAR();
      var12[0].sqltype = 601;
      var12[0].sqllen = 68;
      var12[0].sqlind = -1;
      var12[0].sqlname = "PKTABLE_CAT";
      var12[0].relname = "COLUMNINFO";
      var12[1] = new interbase.interclient.XSQLVAR();
      var12[1].sqltype = 601;
      var12[1].sqllen = 68;
      var12[1].sqlind = -1;
      var12[1].sqlname = "PKTABLE_SCHEM";
      var12[1].relname = "COLUMNINFO";
      var12[2] = new interbase.interclient.XSQLVAR();
      var12[2].sqltype = 600;
      var12[2].sqllen = 68;
      var12[2].sqlind = 0;
      var12[2].sqlname = "PKTABLE_NAME";
      var12[2].relname = "COLUMNINFO";
      var12[3] = new interbase.interclient.XSQLVAR();
      var12[3].sqltype = 600;
      var12[3].sqllen = 68;
      var12[3].sqlind = 0;
      var12[3].sqlname = "PKCOLUMN_NAME";
      var12[3].relname = "COLUMNINFO";
      var12[4] = new interbase.interclient.XSQLVAR();
      var12[4].sqltype = 601;
      var12[4].sqllen = 68;
      var12[4].sqlind = -1;
      var12[4].sqlname = "FKTABLE_CAT";
      var12[4].relname = "COLUMNINFO";
      var12[5] = new interbase.interclient.XSQLVAR();
      var12[5].sqltype = 601;
      var12[5].sqllen = 68;
      var12[5].sqlind = -1;
      var12[5].sqlname = "FKTABLE_SCHEM";
      var12[5].relname = "COLUMNINFO";
      var12[6] = new interbase.interclient.XSQLVAR();
      var12[6].sqltype = 600;
      var12[6].sqllen = 68;
      var12[6].sqlind = 0;
      var12[6].sqlname = "FKTABLE_NAME";
      var12[6].relname = "COLUMNINFO";
      var12[7] = new interbase.interclient.XSQLVAR();
      var12[7].sqltype = 600;
      var12[7].sqllen = 68;
      var12[7].sqlind = 0;
      var12[7].sqlname = "FKCOLUMN_NAME";
      var12[7].relname = "COLUMNINFO";
      var12[8] = new interbase.interclient.XSQLVAR();
      var12[8].sqltype = 500;
      var12[8].sqlname = "KEY_SEQ";
      var12[8].relname = "COLUMNINFO";
      var12[9] = new interbase.interclient.XSQLVAR();
      var12[9].sqltype = 500;
      var12[9].sqlname = "UPDATE_RULE";
      var12[9].relname = "COLUMNINFO";
      var12[10] = new interbase.interclient.XSQLVAR();
      var12[10].sqltype = 500;
      var12[10].sqlname = "DELETE_RULE";
      var12[10].relname = "COLUMNINFO";
      var12[11] = new interbase.interclient.XSQLVAR();
      var12[11].sqltype = 601;
      var12[11].sqllen = 68;
      var12[11].sqlind = 0;
      var12[11].sqlname = "FK_NAME";
      var12[11].relname = "COLUMNINFO";
      var12[12] = new interbase.interclient.XSQLVAR();
      var12[12].sqltype = 601;
      var12[12].sqllen = 68;
      var12[12].sqlind = 0;
      var12[12].sqlname = "PK_NAME";
      var12[12].relname = "COLUMNINFO";
      var12[13] = new interbase.interclient.XSQLVAR();
      var12[13].sqltype = 500;
      var12[13].sqlname = "DEFERRABILITY";
      var12[13].relname = "COLUMNINFO";
      ArrayList var13 = new ArrayList();

      while(var11.next()) {
         Object[] var14 = new Object[]{null, null, var11.getString("PKTABLE_NAME").trim(), var11.getString("PKCOLUMN_NAME").trim(), null, null, var11.getString("FKTABLE_NAME").trim(), var11.getString("FKCOLUMN_NAME").trim(), new Short(var11.getShort("KEY_SEQ")), new Short(this.mapRefConstraints(var11.getString("UPDATE_RULE"))), new Short(this.mapRefConstraints(var11.getString("DELETE_RULE"))), var11.getString("FK_NAME"), var11.getString("PK_NAME"), new Short((short)0)};
         var13.add(var14);
      }

      this.setCannedResult(var12, var13, 14);
      var11.close();
      this.pstmt_.close();
      return this.rs_;
   }

   private Short createShort(int var1) throws SQLException {
      if (var1 > 32767) {
         throw new SQLException("Cannot convert integer to short.");
      } else {
         return new Short((short)var1);
      }
   }

   public synchronized java.sql.ResultSet getTypeInfo() throws SQLException {
      this.checkForClosedConnection();
      interbase.interclient.XSQLVAR[] var2 = new interbase.interclient.XSQLVAR[18];
      var2[0] = new interbase.interclient.XSQLVAR();
      var2[0].sqltype = 600;
      var2[0].sqllen = 68;
      var2[0].sqlind = 0;
      var2[0].sqlname = "TYPE_NAME";
      var2[0].relname = "RDB$TYPEINFO";
      var2[1] = new interbase.interclient.XSQLVAR();
      var2[1].sqltype = 500;
      var2[1].sqlname = "DATA_TYPE";
      var2[1].relname = "RDB$TYPEINFO";
      var2[2] = new interbase.interclient.XSQLVAR();
      var2[2].sqltype = 496;
      var2[2].sqlname = "PRECISION";
      var2[2].relname = "RDB$TYPEINFO";
      var2[3] = new interbase.interclient.XSQLVAR();
      var2[3].sqltype = 600;
      var2[3].sqllen = 1;
      var2[3].sqlind = -1;
      var2[3].sqlname = "LITERAL_PREFIX";
      var2[3].relname = "RDB$TYPEINFO";
      var2[4] = new interbase.interclient.XSQLVAR();
      var2[4].sqltype = 600;
      var2[4].sqllen = 1;
      var2[4].sqlind = -1;
      var2[4].sqlname = "LITERAL_SUFFIX";
      var2[4].relname = "RDB$TYPEINFO";
      var2[5] = new interbase.interclient.XSQLVAR();
      var2[5].sqltype = 600;
      var2[5].sqllen = 68;
      var2[5].sqlind = -1;
      var2[5].sqlname = "CREATE_PARAMS";
      var2[5].relname = "RDB$TYPEINFO";
      var2[6] = new interbase.interclient.XSQLVAR();
      var2[6].sqltype = 500;
      var2[6].sqlname = "NULLABLE";
      var2[6].relname = "RDB$TYPEINFO";
      var2[7] = new interbase.interclient.XSQLVAR();
      var2[7].sqltype = 500;
      var2[7].sqlname = "CASE_SENSITIVE";
      var2[7].relname = "RDB$TYPEINFO";
      var2[8] = new interbase.interclient.XSQLVAR();
      var2[8].sqltype = 500;
      var2[8].sqlname = "SEARCHABLE";
      var2[8].relname = "RDB$TYPEINFO";
      var2[9] = new interbase.interclient.XSQLVAR();
      var2[9].sqltype = 500;
      var2[9].sqlname = "UNSIGNED_ATTRIBUTE";
      var2[9].relname = "RDB$TYPEINFO";
      var2[10] = new interbase.interclient.XSQLVAR();
      var2[10].sqltype = 500;
      var2[10].sqlname = "FIXED_PREC_SCALE";
      var2[10].relname = "RDB$TYPEINFO";
      var2[11] = new interbase.interclient.XSQLVAR();
      var2[11].sqltype = 500;
      var2[11].sqlname = "AUTO_INCREMENT";
      var2[11].relname = "RDB$TYPEINFO";
      var2[12] = new interbase.interclient.XSQLVAR();
      var2[12].sqltype = 600;
      var2[12].sqllen = 68;
      var2[12].sqlind = -1;
      var2[12].sqlname = "LOCAL_TYPE_NAME";
      var2[12].relname = "RDB$TYPEINFO";
      var2[13] = new interbase.interclient.XSQLVAR();
      var2[13].sqltype = 500;
      var2[13].sqlname = "MINIMUM_SCALE";
      var2[13].relname = "RDB$TYPEINFO";
      var2[14] = new interbase.interclient.XSQLVAR();
      var2[14].sqltype = 500;
      var2[14].sqlname = "MAXIMUM_SCALE";
      var2[14].relname = "RDB$TYPEINFO";
      var2[15] = new interbase.interclient.XSQLVAR();
      var2[15].sqltype = 496;
      var2[15].sqlname = "SQL_DATA_TYPE";
      var2[15].relname = "RDB$TYPEINFO";
      var2[16] = new interbase.interclient.XSQLVAR();
      var2[16].sqltype = 496;
      var2[16].sqlname = "SQL_DATETIME_SUB";
      var2[16].relname = "RDB$TYPEINFO";
      var2[17] = new interbase.interclient.XSQLVAR();
      var2[17].sqltype = 496;
      var2[17].sqlname = "NUM_PREC_RADIX";
      var2[17].relname = "RDB$TYPEINFO";
      ArrayList var3 = new ArrayList();
      var3.add(new Object[]{"SMALLINT", this.createShort(-7), new Integer(5), null, null, null, NULLABLE, CASEINSENSITIVE, PREDBASIC, SIGNED, FIXEDSCALE, NOTAUTOINC, "SMALLINT", shortZero, shortZero, new Integer(500), null, DECIMAL});
      var3.add(new Object[]{"SMALLINT", new Short((short)-6), new Integer(5), null, null, null, NULLABLE, CASEINSENSITIVE, PREDBASIC, SIGNED, FIXEDSCALE, NOTAUTOINC, "SMALLINT", shortZero, shortZero, new Integer(500), null, DECIMAL});
      var3.add(new Object[]{"NUMERIC", this.createShort(-5), new Integer(18), null, null, null, NULLABLE, CASEINSENSITIVE, PREDBASIC, SIGNED, FIXEDSCALE, NOTAUTOINC, "NUMERIC", shortZero, shortZero, new Integer(580), null, DECIMAL});
      var3.add(new Object[]{"BLOB", this.createShort(-4), new Integer(0), null, null, "SUB_TYPE 0", NULLABLE, CASESENSITIVE, PREDNONE, UNSIGNED, FIXEDSCALE, NOTAUTOINC, "BLOB", shortZero, shortZero, new Integer(520), null, DECIMAL});
      var3.add(new Object[]{"BLOB", this.createShort(-3), new Integer(0), null, null, "SUB_TYPE 0", NULLABLE, CASESENSITIVE, PREDNONE, UNSIGNED, FIXEDSCALE, NOTAUTOINC, "BLOB", shortZero, shortZero, new Integer(520), null, DECIMAL});
      var3.add(new Object[]{"BLOB", this.createShort(-2), new Integer(0), null, null, null, NULLABLE, CASESENSITIVE, PREDNONE, UNSIGNED, FIXEDSCALE, NOTAUTOINC, "BLOB", shortZero, shortZero, new Integer(520), null, DECIMAL});
      var3.add(new Object[]{"BLOB", this.createShort(-1), new Integer(0), null, null, "SUB_TYPE 1", NULLABLE, CASESENSITIVE, PREDNONE, UNSIGNED, FIXEDSCALE, NOTAUTOINC, "BLOB", shortZero, shortZero, new Integer(520), null, DECIMAL});
      var3.add(new Object[]{"CHAR", this.createShort(1), new Integer(0), "'", "'", null, NULLABLE, CASESENSITIVE, SEARCHABLE, UNSIGNED, FIXEDSCALE, NOTAUTOINC, "CHAR", shortZero, shortZero, new Integer(452), null, DECIMAL});
      var3.add(new Object[]{"NUMERIC", this.createShort(2), new Integer(18), null, null, null, NULLABLE, CASEINSENSITIVE, SEARCHABLE, SIGNED, FIXEDSCALE, NOTAUTOINC, "NUMERIC", shortZero, this.createShort(18), new Integer(580), null, DECIMAL});
      var3.add(new Object[]{"DECIMAL", this.createShort(3), new Integer(18), null, null, null, NULLABLE, CASEINSENSITIVE, SEARCHABLE, SIGNED, FIXEDSCALE, NOTAUTOINC, DECIMAL, shortZero, this.createShort(18), new Integer(580), null, DECIMAL});
      var3.add(new Object[]{"INTEGER", this.createShort(4), new Integer(32), null, null, null, NULLABLE, CASEINSENSITIVE, SEARCHABLE, SIGNED, FIXEDSCALE, NOTAUTOINC, "INTEGER", shortZero, shortZero, new Integer(496), null, DECIMAL});
      var3.add(new Object[]{"SMALLINT", this.createShort(5), new Integer(16), null, null, null, NULLABLE, CASEINSENSITIVE, SEARCHABLE, SIGNED, FIXEDSCALE, NOTAUTOINC, "SMALLINT", shortZero, shortZero, new Integer(500), null, DECIMAL});
      var3.add(new Object[]{"FLOAT", this.createShort(6), new Integer(7), null, null, null, NULLABLE, CASEINSENSITIVE, SEARCHABLE, SIGNED, VARIABLESCALE, NOTAUTOINC, "FLOAT", this.createShort(0), this.createShort(7), new Integer(482), null, DECIMAL});
      var3.add(new Object[]{"DOUBLE PRECISION", this.createShort(8), new Integer(15), null, null, null, NULLABLE, CASEINSENSITIVE, SEARCHABLE, SIGNED, VARIABLESCALE, NOTAUTOINC, "DOUBLE PRECISION", this.createShort(0), this.createShort(15), new Integer(480), null, DECIMAL});
      var3.add(new Object[]{"VARCHAR", this.createShort(12), new Integer(0), "'", "'", null, NULLABLE, CASESENSITIVE, SEARCHABLE, UNSIGNED, FIXEDSCALE, NOTAUTOINC, "VARCHAR", shortZero, shortZero, new Integer(448), null, DECIMAL});
      var3.add(new Object[]{"DATE", this.createShort(91), new Integer(0), null, null, null, NULLABLE, CASEINSENSITIVE, SEARCHABLE, UNSIGNED, FIXEDSCALE, NOTAUTOINC, "DATE", shortZero, shortZero, new Integer(570), null, DECIMAL});
      var3.add(new Object[]{"TIME", this.createShort(92), new Integer(0), null, null, null, NULLABLE, CASEINSENSITIVE, SEARCHABLE, UNSIGNED, FIXEDSCALE, NOTAUTOINC, "TIME", shortZero, shortZero, new Integer(560), null, DECIMAL});
      var3.add(new Object[]{"TIMESTAMP", this.createShort(93), new Integer(0), null, null, null, NULLABLE, CASEINSENSITIVE, SEARCHABLE, UNSIGNED, FIXEDSCALE, NOTAUTOINC, "TIMESTAMP", shortZero, shortZero, new Integer(510), null, DECIMAL});
      var3.add(new Object[]{"BLOB", this.createShort(2004), new Integer(0), null, null, null, NULLABLE, CASESENSITIVE, PREDNONE, UNSIGNED, FIXEDSCALE, NOTAUTOINC, "BLOB", shortZero, shortZero, new Integer(520), null, DECIMAL});
      this.setCannedResult(var2, var3, 18);
      return this.rs_;
   }

   public synchronized java.sql.ResultSet getIndexInfo(String var1, String var2, String var3, boolean var4, boolean var5) throws SQLException {
      this.checkForClosedConnection();
      this.systemTableQueryPreamble(var1, var2);
      StringBuffer var6 = new StringBuffer("select ind.RDB$RELATION_NAME AS TABLE_NAME  ,ind.RDB$UNIQUE_FLAG AS NON_UNIQUE  ,ind.RDB$INDEX_NAME as INDEX_NAME  ,ise.rdb$field_position as ORDINAL_POSITION  ,ise.rdb$field_name as COLUMN_NAME  ,ind.rdb$index_type as ASC_OR_DESC  ,COUNT (DISTINCT P.RDB$PAGE_NUMBER) as IN_PAGES  from rdb$indices ind, rdb$index_segments ise,RDB$PAGES P, RDB$RELATIONS R  where ind.rdb$index_name = ise.rdb$index_name  and ind.rdb$relation_name = ?   AND P.RDB$RELATION_ID = R.RDB$RELATION_ID  AND R.RDB$RELATION_NAME = IND.RDB$RELATION_NAME  AND (P.RDB$PAGE_TYPE = 7 OR P.RDB$PAGE_TYPE = 6) ");
      if (var4) {
         var6.append("and ind.RDB$UNIQUE_FLAG = 1 ");
      }

      var6.append(" group by IND.RDB$INDEX_NAME, IND.RDB$RELATION_NAME,  IND.RDB$UNIQUE_FLAG, ISE.RDB$FIELD_POSITION, ISE.RDB$FIELD_NAME,  IND.RDB$INDEX_TYPE, IND.RDB$SEGMENT_COUNT  order by 2, 3, 4");
      this.pstmt_ = (PreparedStatement)this.connection_.prepareStatement(3, var6.toString());
      String var7;
      if ("".equals(var3)) {
         var7 = "%";
      } else {
         var7 = var3;
      }

      this.pstmt_.setString(1, var7);
      interbase.interclient.ResultSet var8 = (interbase.interclient.ResultSet)this.pstmt_.executeQuery();
      interbase.interclient.XSQLVAR[] var9 = new interbase.interclient.XSQLVAR[13];
      var9[0] = new interbase.interclient.XSQLVAR();
      var9[0].sqltype = 601;
      var9[0].sqllen = 68;
      var9[0].sqlind = -1;
      var9[0].sqlname = "TABLE_CAT";
      var9[0].relname = "INDEXINFO";
      var9[1] = new interbase.interclient.XSQLVAR();
      var9[1].sqltype = 601;
      var9[1].sqllen = 68;
      var9[1].sqlind = -1;
      var9[1].sqlname = "TABLE_SCHEM";
      var9[1].relname = "INDEXINFO";
      var9[2] = new interbase.interclient.XSQLVAR();
      var9[2].sqltype = 600;
      var9[2].sqllen = 68;
      var9[2].sqlind = 0;
      var9[2].sqlname = "TABLE_NAME";
      var9[2].relname = "INDEXINFO";
      var9[3] = new interbase.interclient.XSQLVAR();
      var9[3].sqltype = 500;
      var9[3].sqlname = "NON_UNIQUE";
      var9[3].relname = "INDEXINFO";
      var9[4] = new interbase.interclient.XSQLVAR();
      var9[4].sqltype = 601;
      var9[4].sqllen = 68;
      var9[4].sqlind = -1;
      var9[4].sqlname = "INDEX_QUALIFIER";
      var9[4].relname = "INDEXINFO";
      var9[5] = new interbase.interclient.XSQLVAR();
      var9[5].sqltype = 600;
      var9[5].sqllen = 68;
      var9[5].sqlind = -1;
      var9[5].sqlname = "INDEX_NAME";
      var9[5].relname = "INDEXINFO";
      var9[6] = new interbase.interclient.XSQLVAR();
      var9[6].sqltype = 500;
      var9[6].sqlname = "TYPE";
      var9[6].relname = "INDEXINFO";
      var9[7] = new interbase.interclient.XSQLVAR();
      var9[7].sqltype = 500;
      var9[7].sqlname = "ORDINAL_POSITION";
      var9[7].relname = "INDEXINFO";
      var9[8] = new interbase.interclient.XSQLVAR();
      var9[8].sqltype = 600;
      var9[8].sqllen = 68;
      var9[8].sqlind = 0;
      var9[8].sqlname = "COLUMN_NAME";
      var9[8].relname = "INDEXINFO";
      var9[9] = new interbase.interclient.XSQLVAR();
      var9[9].sqltype = 600;
      var9[9].sqllen = 1;
      var9[9].sqlind = -1;
      var9[9].sqlname = "ASC_OR_DESC";
      var9[9].relname = "INDEXINFO";
      var9[10] = new interbase.interclient.XSQLVAR();
      var9[10].sqltype = 496;
      var9[10].sqlname = "CARDINALITY";
      var9[10].relname = "INDEXINFO";
      var9[11] = new interbase.interclient.XSQLVAR();
      var9[11].sqltype = 496;
      var9[11].sqlname = "PAGES";
      var9[11].relname = "INDEXINFO";
      var9[12] = new interbase.interclient.XSQLVAR();
      var9[12].sqltype = 601;
      var9[12].sqllen = 68;
      var9[12].sqlind = -1;
      var9[12].sqlname = "FILTER_CONDITION";
      var9[12].relname = "INDEXINFO";
      ArrayList var10 = new ArrayList();

      while(var8.next()) {
         Object[] var11 = new Object[]{null, null, var8.getString("TABLE_NAME").trim(), new Short((short)(var8.getShort("NON_UNIQUE") == 1 ? 0 : 1)), null, var8.getString("INDEX_NAME").trim(), new Short((short)3), new Short((short)(var8.getShort("ORDINAL_POSITION") + 1)), var8.getString("COLUMN_NAME").trim(), new String(var8.getShort("ASC_OR_DESC") == 0 ? "A" : "D"), new Integer(0), new Integer(var8.getInt("IN_PAGES")), null};
         var10.add(var11);
      }

      this.setCannedResult(var9, var10, 13);
      var8.close();
      this.pstmt_.close();
      return this.rs_;
   }

   public synchronized boolean supportsResultSetType(int var1) throws SQLException {
      return var1 == 1003;
   }

   public synchronized boolean supportsResultSetConcurrency(int var1, int var2) throws SQLException {
      return (var1 == 1003 || var1 == 1004) && var2 == 1007;
   }

   public synchronized boolean ownUpdatesAreVisible(int var1) throws SQLException {
      return false;
   }

   public synchronized boolean ownDeletesAreVisible(int var1) throws SQLException {
      return false;
   }

   public synchronized boolean ownInsertsAreVisible(int var1) throws SQLException {
      return false;
   }

   public synchronized boolean othersUpdatesAreVisible(int var1) throws SQLException {
      return false;
   }

   public synchronized boolean othersDeletesAreVisible(int var1) throws SQLException {
      return false;
   }

   public synchronized boolean othersInsertsAreVisible(int var1) throws SQLException {
      return false;
   }

   public synchronized boolean updatesAreDetected(int var1) throws SQLException {
      return false;
   }

   public synchronized boolean deletesAreDetected(int var1) throws SQLException {
      return false;
   }

   public synchronized boolean insertsAreDetected(int var1) throws SQLException {
      return false;
   }

   public synchronized boolean supportsBatchUpdates() throws SQLException {
      return true;
   }

   public synchronized java.sql.ResultSet getUDTs(String var1, String var2, String var3, int[] var4) throws SQLException {
      interbase.interclient.XSQLVAR[] var5 = new interbase.interclient.XSQLVAR[6];
      var5[0] = new interbase.interclient.XSQLVAR();
      var5[0].sqltype = 601;
      var5[0].sqllen = 68;
      var5[0].sqlind = -1;
      var5[0].sqlname = "TYPE_CAT";
      var5[0].relname = "";
      var5[1] = new interbase.interclient.XSQLVAR();
      var5[1].sqltype = 601;
      var5[1].sqllen = 68;
      var5[1].sqlind = -1;
      var5[1].sqlname = "TYPE_SCHEM";
      var5[1].relname = "";
      var5[2] = new interbase.interclient.XSQLVAR();
      var5[2].sqltype = 600;
      var5[2].sqllen = 68;
      var5[2].sqlind = 0;
      var5[2].sqlname = "TYPE_NAME";
      var5[2].relname = "";
      var5[3] = new interbase.interclient.XSQLVAR();
      var5[3].sqltype = 600;
      var5[3].sqllen = 68;
      var5[3].sqlind = 0;
      var5[3].sqlname = "CLASS_NAME";
      var5[3].relname = "";
      var5[4] = new interbase.interclient.XSQLVAR();
      var5[4].sqltype = 500;
      var5[4].sqlname = "DATA_TYPE";
      var5[4].relname = "";
      var5[5] = new interbase.interclient.XSQLVAR();
      var5[5].sqltype = 601;
      var5[5].sqllen = 68;
      var5[5].sqlind = 0;
      var5[5].sqlname = "REMARKS";
      var5[5].relname = "";
      ArrayList var6 = new ArrayList(0);
      this.setCannedResult(var5, var6, 6);
      return this.rs_;
   }

   public synchronized java.sql.Connection getConnection() throws SQLException {
      return this.connection_;
   }

   public Date getDriverExpirationDate() {
      return Globals.interclientExpirationDate__;
   }

   public int getDatabaseMajorVersion() throws SQLException {
      this.checkForClosedConnection();
      return this.ibMajorVersion_;
   }

   public int getODSMajorVersion() throws SQLException {
      this.checkForClosedConnection();
      return this.odsMajorVersion_;
   }

   public int getODSMinorVersion() throws SQLException {
      this.checkForClosedConnection();
      return this.odsMinorVersion_;
   }

   public synchronized int getActualCachePagesInUse() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized int getPersistentDatabaseCachePages() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public int getPageAllocation() throws SQLException {
      this.checkForClosedConnection();
      return this.pageAllocation_;
   }

   public int getPageSize() throws SQLException {
      this.checkForClosedConnection();
      return this.pageSize_;
   }

   public synchronized boolean getSweepInterval() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized boolean isDatabaseReadWrite() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized boolean reservingSpaceForVersioning() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized boolean usingSynchronousWrites() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized DatabaseStatistics getStatistics() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized String[] getUsersConnected() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized String getUDFs() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   private String systemTableValue(String var1) {
      return var1.length() > 2 && var1.startsWith("\"") && var1.endsWith("\"") ? var1.substring(1, var1.length() - 1) : var1.toUpperCase();
   }

   public synchronized boolean supportsSavepoints() throws SQLException {
      return false;
   }

   public synchronized boolean supportsNamedParameters() throws SQLException {
      //TODO::implement real method
      return false;
//      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized boolean supportsMultipleOpenResults() throws SQLException {
      return false;
   }

   public synchronized boolean supportsGetGeneratedKeys() throws SQLException {
      return false;
   }

   public synchronized java.sql.ResultSet getSuperTypes(String var1, String var2, String var3) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.ResultSet getSuperTables(String var1, String var2, String var3) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.ResultSet getAttributes(String var1, String var2, String var3, String var4) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized boolean supportsResultSetHoldability(int var1) throws SQLException {
      return false;
   }

   public synchronized int getResultSetHoldability() throws SQLException {
      if (this.rs_ != null) {
         ResultSet var10000 = this.rs_;
         return 2;
      } else {
         return 2;
      }
   }

   public synchronized int getDatabaseMinorVersion() throws SQLException {
      return this.ibMinorVersion_;
   }

   public synchronized int getJDBCMajorVersion() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized int getJDBCMinorVersion() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized int getSQLStateType() throws SQLException {
      //TODO::implement real method
      return DatabaseMetaData.sqlStateSQL;
   }

   public synchronized boolean locatorsUpdateCopy() throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized boolean supportsStatementPooling() throws SQLException {
      return false;
   }

   @Override
   public RowIdLifetime getRowIdLifetime() throws SQLException {
      return null;
   }

   @Override
   public java.sql.ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
      return null;
   }

   @Override
   public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
      return false;
   }

   @Override
   public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
      return false;
   }

   @Override
   public java.sql.ResultSet getClientInfoProperties() throws SQLException {
      return null;
   }

   @Override
   public java.sql.ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
      return null;
   }

   @Override
   public java.sql.ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
      return null;
   }

   @Override
   public java.sql.ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
      return null;
   }

   @Override
   public boolean generatedKeyAlwaysReturned() throws SQLException {
      return false;
   }

   @Override
   public <T> T unwrap(Class<T> iface) throws SQLException {
      return null;
   }

   @Override
   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return false;
   }

   private class SqlPattern {
      private String clause = "";
      private String pattern;

      public SqlPattern(String var2, String var3) {
         if (var3 != null) {
            if (!"%".equals(var3) && !"".equals(var3)) {
               if (DatabaseMetaData.this.exactMatch(var3)) {
                  this.pattern = DatabaseMetaData.this.systemTableValue(DatabaseMetaData.this.stripEscape(var3));
                  this.clause = var2 + " = ? and ";
               } else {
                  this.pattern = DatabaseMetaData.this.systemTableValue(var3);
                  this.clause = var2 + " || '" + "                               " + "' like ? escape '\\' and ";
               }

            }
         }
      }

      public String getClause() {
         return this.clause;
      }

      public String getPattern() {
         return this.pattern;
      }
   }
}
