package interbase.interclient;

class ErrorKey {
   private String resourceKey_;
   private String sqlState_;
   private int errorCode_;
   private static final String sqlState__featureNotSupported__ = "0A000";
   private static final String sqlState__interbase_error__ = "ICI00";
   private static final String lastUsedICJ = "00";
   static final ErrorKey engine__default_0__ = new ErrorKey("2", (String)null, 0);
   static final ErrorKey invalidOperation__connection_closed__ = new ErrorKey("3", "ICJ01", 2);
   static final ErrorKey invalidOperation__server_connection_closed__ = new ErrorKey("103", "ICJ02", 2);
   static final ErrorKey invalidOperation__result_set_closed__ = new ErrorKey("4", "ICJ03", 2);
   static final ErrorKey invalidOperation__statement_closed__ = new ErrorKey("5", "ICJ04", 2);
   static final ErrorKey invalidOperation__transaction_in_progress__ = new ErrorKey("6", "ICJ05", 2);
   static final ErrorKey invalidOperation__commit_or_rollback_under_autocommit__ = new ErrorKey("7", "ICJ06", 2);
   static final ErrorKey invalidOperation__execute_query_on_an_update_statement__ = new ErrorKey("8", "ICJ07", 2);
   static final ErrorKey invalidOperation__set_null_on_non_nullable_parameter__ = new ErrorKey("9", "ICJ08", 2);
   static final ErrorKey invalidOperation__read_at_end_of_cursor__ = new ErrorKey("10", "ICJ09", 2);
   static final ErrorKey invalidOperation__read_at_invalid_cursor_position__ = new ErrorKey("11", "ICJ0A", 2);
   static final ErrorKey invalidOperation__was_null_with_no_data_retrieved__ = new ErrorKey("12", "ICJ0B", 2);
   static final ErrorKey invalidOperation__parameter_not_set__ = new ErrorKey("13", "ICJ0C", 2);
   static final ErrorKey invalidOperation__execute_update_on_an_select_statement__ = new ErrorKey("8", "ICJ0D", 2);
   static final ErrorKey parameterConversion__type_conversion__ = new ErrorKey("14", "ICJ10", 2);
   static final ErrorKey parameterConversion__type_conversion__set_number_on_binary_blob__ = new ErrorKey("98", "ICJ11", 2);
   static final ErrorKey parameterConversion__type_conversion__set_date_on_binary_blob__ = new ErrorKey("99", "ICJ12", 2);
   static final ErrorKey parameterConversion__set_object_on_stream__ = new ErrorKey("104", "ICJ13", 2);
   static final ErrorKey parameterConversion__instance_conversion_0__ = new ErrorKey("15", "ICJ14", 2);
   static final ErrorKey parameterConversion__array_element_type_conversion__ = new ErrorKey("116", "ICJ15", 2);
   static final ErrorKey parameterConversion__array_element_instance_conversion_0__ = new ErrorKey("117", "ICJ16", 2);
   static final ErrorKey parameterConversion__array_element_instance_truncation_0__ = new ErrorKey("118", "ICJ17", 2);
   static final ErrorKey columnConversion__type_conversion__ = new ErrorKey("16", "ICJ20", 2);
   static final ErrorKey columnConversion__type_conversion__get_number_on_binary_blob__ = new ErrorKey("100", "ICJ21", 2);
   static final ErrorKey columnConversion__type_conversion__get_date_on_binary_blob__ = new ErrorKey("101", "ICJ22", 2);
   static final ErrorKey columnConversion__instance_conversion_0__ = new ErrorKey("17", "ICJ23", 2);
   static final ErrorKey invalidArgument__isolation_0__ = new ErrorKey("18", "ICJ30", 2);
   static final ErrorKey invalidArgument__connection_property__isolation__ = new ErrorKey("19", "ICJ31", 2);
   static final ErrorKey invalidArgument__connection_property__lock_resolution_mode__ = new ErrorKey("20", "ICJ32", 2);
   static final ErrorKey invalidArgument__connection_property__unrecognized__ = new ErrorKey("21", "ICJ33", 2);
   static final ErrorKey invalidArgument__connection_properties__no_user_or_password__ = new ErrorKey("22", "ICJ34", 2);
   static final ErrorKey invalidArgument__connection_properties__null__ = new ErrorKey("92", "ICJ35", 2);
   static final ErrorKey invalidArgument__sql_empty_or_null__ = new ErrorKey("23", "ICJ37", 2);
   static final ErrorKey invalidArgument__column_name_0__ = new ErrorKey("24", "ICJ38", 2);
   static final ErrorKey invalidArgument__negative_row_fetch_size__ = new ErrorKey("25", "ICJ39", 2);
   static final ErrorKey invalidArgument__negative_max_rows__ = new ErrorKey("26", "ICJ3A", 2);
   static final ErrorKey invalidArgument__fetch_size_exceeds_max_rows__ = new ErrorKey("27", "ICJ3B", 2);
   static final ErrorKey invalidArgument__setUnicodeStream_odd_bytes__ = new ErrorKey("91", "ICJ3C", 2);
   static final ErrorKey invalidArgument__not_array_column__ = new ErrorKey("123", "ICJ3D", 2);
   static final ErrorKey invalidArgument__not_array_parameter__ = new ErrorKey("122", "ICJ3E", 2);
   static final ErrorKey invalidArgument__invalid_array_slice__ = new ErrorKey("121", "ICJ3F", 2);
   static final ErrorKey invalidArgument__invalid_array_dimensions__ = new ErrorKey("120", "ICJ3G", 2);
   static final ErrorKey invalidArgument__lock_resolution__ = new ErrorKey("125", "ICJ3H", 2);
   static final ErrorKey invalidArgument__version_acknowledgement_mode__ = new ErrorKey("126", "ICJ3I", 2);
   static final ErrorKey invalidArgument__table_lock__ = new ErrorKey("127", "ICJ3J", 2);
   static final ErrorKey invalidArgument__connection_properties__sql_dialect__ = new ErrorKey("129", "ICJ3K", 2);
   static final ErrorKey columnIndexOutOfBounds__0__ = new ErrorKey("28", "ICJ40", 2);
   static final ErrorKey parameterIndexOutOfBounds__0__ = new ErrorKey("29", "ICJ50", 2);
   static final ErrorKey urlSyntax__bad_server_prefix_0__ = new ErrorKey("30", "ICJ60", 2);
   static final ErrorKey urlSyntax__bad_server_suffix_0__ = new ErrorKey("31", "ICJ61", 2);
   static final ErrorKey escapeSyntax__no_closing_escape_delimeter_0__ = new ErrorKey("32", "ICJ70", 2);
   static final ErrorKey escapeSyntax__unrecognized_keyword_0__ = new ErrorKey("33", "ICJ71", 2);
   static final ErrorKey escapeSyntax__d_0__ = new ErrorKey("34", "ICJ72", 2);
   static final ErrorKey escapeSyntax__ts_0__ = new ErrorKey("35", "ICJ73", 2);
   static final ErrorKey escapeSyntax__escape_0__ = new ErrorKey("36", "ICJ74", 2);
   static final ErrorKey escapeSyntax__escape__no_quote_0__ = new ErrorKey("37", "ICJ75", 2);
   static final ErrorKey escapeSyntax__fn_0__ = new ErrorKey("38", "ICJ76", 2);
   static final ErrorKey escapeSyntax__call_0__ = new ErrorKey("39", "ICJ77", 2);
   static final ErrorKey escapeSyntax__t_0__ = new ErrorKey("131", "ICJ78", 2);
   static final ErrorKey invalidOperation__statement_notcancelable__ = new ErrorKey("132", "ICJ79", 2);
   static final ErrorKey unlicensedComponent__not_client_server__ = new ErrorKey("40", "ICJ80", 8);
   static final ErrorKey unlicensedComponent__incompatible_ibversion_0__ = new ErrorKey("93", "ICJ81", 8);
   static final ErrorKey unlicensedComponent__interserver__ = new ErrorKey("41", "ICJ82", 8);
   static final ErrorKey expiredDriver__01__ = new ErrorKey("42", "ICJ90", 8);
   static final ErrorKey expiredDriverWarning__01__ = new ErrorKey("43", "01JA0", 8);
   static final ErrorKey bugCheck__0__ = new ErrorKey("44", "ICJB0", 9);
   static final ErrorKey characterEncoding__read_0__ = new ErrorKey("45", "ICJC0", 3);
   static final ErrorKey characterEncoding__write_0__ = new ErrorKey("46", "ICJC1", 3);
   static final ErrorKey remoteProtocol__unexpected_token_from_server_0__ = new ErrorKey("47", "ICJD0", 10);
   static final ErrorKey remoteProtocol__unexpected_token_from_client__ = new ErrorKey("48", "ICJD1", 10);
   static final ErrorKey remoteProtocol__unable_to_establish_protocol__ = new ErrorKey("49", "ICJD2", 10);
   static final ErrorKey remoteProtocol__bad_message_certficate_from_server__ = new ErrorKey("50", "ICJD3", 10);
   static final ErrorKey communication__user_stream__io_exception_on_read_0__ = new ErrorKey("51", "ICJE0", 1);
   static final ErrorKey communication__user_stream__unexpected_eof__ = new ErrorKey("52", "ICJE1", 1);
   static final ErrorKey communication__socket_exception_on_connect_01__ = new ErrorKey("53", "ICJE2", 1);
   static final ErrorKey communication__io_exception_on_connect_01__ = new ErrorKey("54", "ICJE3", 1);
   static final ErrorKey communication__io_exception_on_disconnect_01__ = new ErrorKey("55", "ICJE4", 1);
   static final ErrorKey communication__io_exception_on_recv_protocol_01__ = new ErrorKey("56", "ICJE5", 1);
   static final ErrorKey communication__io_exception_on_recv_message_01__ = new ErrorKey("57", "ICJE6", 1);
   static final ErrorKey communication__io_exception_on_send_message_01__ = new ErrorKey("58", "ICJE7", 1);
   static final ErrorKey communication__io_exception_on_read_0__ = new ErrorKey("59", "ICJE8", 1);
   static final ErrorKey communication__io_exception_on_blob_read_01__ = new ErrorKey("60", "ICJE9", 1);
   static final ErrorKey communication__io_exception_on_blob_close_01__ = new ErrorKey("90", "ICJEA", 1);
   static final ErrorKey communication__interserver__ = new ErrorKey("61", "ICJEB", 1);
   static final ErrorKey socketTimeout__012__ = new ErrorKey("70", "ICJF0", 4);
   static final ErrorKey unknownHost__0__ = new ErrorKey("71", "ICJG0", 5);
   static final ErrorKey badInstallation__unsupported_jdk_version__ = new ErrorKey("72", "ICJH0", 6);
   static final ErrorKey badInstallation__security_check_on_socket_01__ = new ErrorKey("73", "ICJH1", 6);
   static final ErrorKey badInstallation__incompatible_remote_protocols__ = new ErrorKey("74", "ICJH2", 6);
   static final ErrorKey driverNotCapable__out_parameters__ = new ErrorKey("75", "0A000", 7);
   static final ErrorKey driverNotCapable__schemas__ = new ErrorKey("76", "0A000", 7);
   static final ErrorKey driverNotCapable__catalogs__ = new ErrorKey("77", "0A000", 7);
   static final ErrorKey driverNotCapable__isolation__ = new ErrorKey("78", "0A000", 7);
   static final ErrorKey driverNotCapable__binary_literals__ = new ErrorKey("79", "0A000", 7);
   static final ErrorKey driverNotCapable__asynchronous_cancel__ = new ErrorKey("80", "0A000", 7);
   static final ErrorKey driverNotCapable__invalid_conccurency_scrolling__ = new ErrorKey("134", "0A000", 7);
   static final ErrorKey driverNotCapable__scrolling_not_supported__ = new ErrorKey("135", "0A000", 7);
   static final ErrorKey driverNotCapable__query_timeout__ = new ErrorKey("81", "0A000", 7);
   static final ErrorKey driverNotCapable__connection_timeout__ = new ErrorKey("82", "0A000", 7);
   static final ErrorKey driverNotCapable__extension_not_yet_supported__ = new ErrorKey("83", "0A000", 7);
   static final ErrorKey driverNotCapable__jdbc2_not_yet_supported__ = new ErrorKey("105", "0A000", 7);
   static final ErrorKey driverNotCapable__escape__t__ = new ErrorKey("84", "0A000", 7);
   static final ErrorKey driverNotCapable__escape__ts_fractionals__ = new ErrorKey("85", "0A000", 7);
   static final ErrorKey driverNotCapable__escape__call_with_result__ = new ErrorKey("86", "0A000", 7);
   static final ErrorKey driverNotCapable__input_array_metadata__ = new ErrorKey("124", "0A000", 7);
   static final ErrorKey unsupportedCharacterSet__0__ = new ErrorKey("87", "0A000", 7);
   static final ErrorKey unsupportedCharacterEncoding__0__ = new ErrorKey("133", "0A000", 7);
   static final ErrorKey unsupportedSQLDialect__dialect_adjusted__ = new ErrorKey("119", "01JB0", 130);
   static final ErrorKey outOfMemory__ = new ErrorKey("88", "ICJI0", 11);
   static final ErrorKey[] interserverErrorKeys__;

   private ErrorKey(String var1, String var2, int var3) {
      this.resourceKey_ = var1;
      this.sqlState_ = var2;
      this.errorCode_ = var3;
   }

   static final String getIBSQLState() {
      return "ICI00";
   }

   String getResourceKey() {
      return this.resourceKey_;
   }

   String getSQLState() {
      return this.sqlState_;
   }

   String getSQLState(int var1, int var2) {
      return this.sqlState_ != null ? this.sqlState_ : this.mapIBCodesToSQLState(var1, var2);
   }

   private String mapIBCodesToSQLState(int var1, int var2) {
      switch(var1) {
      case 335544333:
         return "ICI00";
      case 335544335:
      case 335544405:
      case 335544649:
         return "ICI00";
      case 335544336:
      case 335544345:
      case 335544451:
         return "ICI00";
      case 335544344:
      case 335544375:
         return "ICI00";
      case 335544389:
      case 335544430:
         return "ICI00";
      case 335544452:
         return "ICI00";
      case 335544472:
         return "ICI00";
      default:
         switch(var2) {
         case 104:
         case 204:
         case 804:
         case 842:
            return "ICI00";
         case 501:
            return "ICI00";
         case 607:
            return "ICI00";
         case 802:
            return "ICI00";
         case 902:
            return "ICI00";
         default:
            return "ICI00";
         }
      }
   }

   int getErrorCode() {
      return this.errorCode_;
   }

   static {
      interserverErrorKeys__ = new ErrorKey[]{engine__default_0__, bugCheck__0__, outOfMemory__, unsupportedCharacterSet__0__, driverNotCapable__extension_not_yet_supported__, driverNotCapable__asynchronous_cancel__, driverNotCapable__isolation__, driverNotCapable__connection_timeout__, unlicensedComponent__interserver__, invalidArgument__connection_property__lock_resolution_mode__, invalidArgument__connection_property__isolation__, invalidArgument__connection_property__unrecognized__, invalidOperation__execute_query_on_an_update_statement__, communication__interserver__, remoteProtocol__unexpected_token_from_client__, communication__interserver__, unsupportedSQLDialect__dialect_adjusted__, invalidOperation__execute_update_on_an_select_statement__};
   }
}
