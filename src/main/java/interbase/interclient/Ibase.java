package interbase.interclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Set;

class Ibase {
   static final int SQL_DIALECT_V5 = 1;
   static final int SQL_DIALECT_V6_TRANSITION = 2;
   static final int SQL_DIALECT_V6 = 3;
   static final int SQL_DIALECT_V71 = 4;
   static final int SQL_DIALECT_CURRENT = 3;
   static final int METADATALENGTH = 69;
   static final int ACT_METADATA = 68;
   static final int PROTOCOL_VERSION11 = 11;
   static final int PROTOCOL_VERSION12 = 12;
   static final int DSQL_close = 1;
   static final int DSQL_drop = 2;
   static final int DSQL_cancel = 4;
   static final int isc_dpb_version1 = 1;
   static final int isc_dpb_cdd_pathname = 1;
   static final int isc_dpb_allocation = 2;
   static final int isc_dpb_journal = 3;
   static final int isc_dpb_page_size = 4;
   static final int isc_dpb_num_buffers = 5;
   static final int isc_dpb_buffer_length = 6;
   static final int isc_dpb_debug = 7;
   static final int isc_dpb_garbage_collect = 8;
   static final int isc_dpb_verify = 9;
   static final int isc_dpb_sweep = 10;
   static final int isc_dpb_enable_journal = 11;
   static final int isc_dpb_disable_journal = 12;
   static final int isc_dpb_dbkey_scope = 13;
   static final int isc_dpb_number_of_users = 14;
   static final int isc_dpb_trace = 15;
   static final int isc_dpb_no_garbage_collect = 16;
   static final int isc_dpb_damaged = 17;
   static final int isc_dpb_license = 18;
   static final int isc_dpb_sys_user_name = 19;
   static final int isc_dpb_encrypt_key = 20;
   static final int isc_dpb_activate_shadow = 21;
   static final int isc_dpb_sweep_interval = 22;
   static final int isc_dpb_delete_shadow = 23;
   static final int isc_dpb_force_write = 24;
   static final int isc_dpb_begin_log = 25;
   static final int isc_dpb_quit_log = 26;
   static final int isc_dpb_no_reserve = 27;
   static final int isc_dpb_user_name = 28;
   static final int isc_dpb_password = 29;
   static final int isc_dpb_password_enc = 30;
   static final int isc_dpb_sys_user_name_enc = 31;
   static final int isc_dpb_interp = 32;
   static final int isc_dpb_online_dump = 33;
   static final int isc_dpb_old_file_size = 34;
   static final int isc_dpb_old_num_files = 35;
   static final int isc_dpb_old_file = 36;
   static final int isc_dpb_old_start_page = 37;
   static final int isc_dpb_old_start_seqno = 38;
   static final int isc_dpb_old_start_file = 39;
   static final int isc_dpb_drop_walfile = 40;
   static final int isc_dpb_old_dump_id = 41;
   static final int isc_dpb_wal_backup_dir = 42;
   static final int isc_dpb_wal_chkptlen = 43;
   static final int isc_dpb_wal_numbufs = 44;
   static final int isc_dpb_wal_bufsize = 45;
   static final int isc_dpb_wal_grp_cmt_wait = 46;
   static final int isc_dpb_lc_messages = 47;
   static final int isc_dpb_lc_ctype = 48;
   static final int isc_dpb_cache_manager = 49;
   static final int isc_dpb_shutdown = 50;
   static final int isc_dpb_online = 51;
   static final int isc_dpb_shutdown_delay = 52;
   static final int isc_dpb_reserved = 53;
   static final int isc_dpb_overwrite = 54;
   static final int isc_dpb_sec_attach = 55;
   static final int isc_dpb_disable_wal = 56;
   static final int isc_dpb_connect_timeout = 57;
   static final int isc_dpb_dummy_packet_interval = 58;
   static final int isc_dpb_gbak_attach = 59;
   static final int isc_dpb_sql_role_name = 60;
   static final int isc_dpb_set_page_buffers = 61;
   static final int isc_dpb_working_directory = 62;
   static final int isc_dpb_sql_dialect = 63;
   static final int isc_dpb_set_db_readonly = 64;
   static final int isc_dpb_set_db_sql_dialect = 65;
   static final int isc_dpb_gfix_attach = 66;
   static final int isc_dpb_gstat_attach = 67;
   static final int isc_dpb_gbak_ods_version = 68;
   static final int isc_dpb_gbak_ods_minor_version = 69;
   static final int isc_tpb_version1 = 1;
   static final int isc_tpb_version3 = 3;
   static final int isc_tpb_consistency = 1;
   static final int isc_tpb_concurrency = 2;
   static final int isc_tpb_shared = 3;
   static final int isc_tpb_protected = 4;
   static final int isc_tpb_exclusive = 5;
   static final int isc_tpb_wait = 6;
   static final int isc_tpb_nowait = 7;
   static final int isc_tpb_read = 8;
   static final int isc_tpb_write = 9;
   static final int isc_tpb_lock_read = 10;
   static final int isc_tpb_lock_write = 11;
   static final int isc_tpb_verb_time = 12;
   static final int isc_tpb_commit_time = 13;
   static final int isc_tpb_ignore_limbo = 14;
   static final int isc_tpb_read_committed = 15;
   static final int isc_tpb_autocommit = 16;
   static final int isc_tpb_rec_version = 17;
   static final int isc_tpb_no_rec_version = 18;
   static final int isc_tpb_restart_requests = 19;
   static final int isc_tpb_no_auto_undo = 20;
   static final byte isc_info_end = 1;
   static final byte isc_info_truncated = 2;
   static final byte isc_info_error = 3;
   static final byte isc_info_data_not_ready = 4;
   static final byte isc_info_flag_end = 127;
   static final int isc_info_db_id = 4;
   static final int isc_info_reads = 5;
   static final int isc_info_writes = 6;
   static final int isc_info_fetches = 7;
   static final int isc_info_marks = 8;
   static final int isc_info_implementation = 11;
   static final int isc_info_version = 12;
   static final int isc_info_base_level = 13;
   static final int isc_info_page_size = 14;
   static final int isc_info_num_buffers = 15;
   static final int isc_info_limbo = 16;
   static final int isc_info_current_memory = 17;
   static final int isc_info_max_memory = 18;
   static final int isc_info_window_turns = 19;
   static final int isc_info_license = 20;
   static final int isc_info_allocation = 21;
   static final int isc_info_attachment_id = 22;
   static final int isc_info_read_seq_count = 23;
   static final int isc_info_read_idx_count = 24;
   static final int isc_info_insert_count = 25;
   static final int isc_info_update_count = 26;
   static final int isc_info_delete_count = 27;
   static final int isc_info_backout_count = 28;
   static final int isc_info_purge_count = 29;
   static final int isc_info_expunge_count = 30;
   static final int isc_info_sweep_interval = 31;
   static final int isc_info_ods_version = 32;
   static final int isc_info_ods_minor_version = 33;
   static final int isc_info_no_reserve = 34;
   static final int isc_info_logfile = 35;
   static final int isc_info_cur_logfile_name = 36;
   static final int isc_info_cur_log_part_offset = 37;
   static final int isc_info_num_wal_buffers = 38;
   static final int isc_info_wal_buffer_size = 39;
   static final int isc_info_wal_ckpt_length = 40;
   static final int isc_info_wal_cur_ckpt_interval = 41;
   static final int isc_info_wal_prv_ckpt_fname = 42;
   static final int isc_info_wal_prv_ckpt_poffset = 43;
   static final int isc_info_wal_recv_ckpt_fname = 44;
   static final int isc_info_wal_recv_ckpt_poffset = 45;
   static final int isc_info_wal_grpc_wait_usecs = 47;
   static final int isc_info_wal_num_io = 48;
   static final int isc_info_wal_avg_io_size = 49;
   static final int isc_info_wal_num_commits = 50;
   static final int isc_info_wal_avg_grpc_size = 51;
   static final int isc_info_forced_writes = 52;
   static final int isc_info_user_names = 53;
   static final int isc_info_page_errors = 54;
   static final int isc_info_record_errors = 55;
   static final int isc_info_bpage_errors = 56;
   static final int isc_info_dpage_errors = 57;
   static final int isc_info_ipage_errors = 58;
   static final int isc_info_ppage_errors = 59;
   static final int isc_info_tpage_errors = 60;
   static final int isc_info_set_page_buffers = 61;
   static final int isc_info_db_sql_dialect = 62;
   static final int isc_info_db_read_only = 63;
   static final int isc_info_db_size_in_pages = 64;
   static final int isc_info_db_reads = 65;
   static final int isc_info_db_writes = 66;
   static final int isc_info_db_fetches = 67;
   static final int isc_info_db_marks = 68;
   static final int isc_info_db_group_commit = 69;
   static final int isc_info_att_charset = 70;
   static final int isc_info_svr_min_ver = 71;
   static final byte isc_info_sql_select = 4;
   static final byte isc_info_sql_bind = 5;
   static final byte isc_info_sql_num_variables = 6;
   static final byte isc_info_sql_describe_vars = 7;
   static final byte isc_info_sql_describe_end = 8;
   static final byte isc_info_sql_sqlda_seq = 9;
   static final byte isc_info_sql_message_seq = 10;
   static final byte isc_info_sql_type = 11;
   static final byte isc_info_sql_sub_type = 12;
   static final byte isc_info_sql_scale = 13;
   static final byte isc_info_sql_length = 14;
   static final byte isc_info_sql_null_ind = 15;
   static final byte isc_info_sql_field = 16;
   static final byte isc_info_sql_relation = 17;
   static final byte isc_info_sql_owner = 18;
   static final byte isc_info_sql_alias = 19;
   static final byte isc_info_sql_sqlda_start = 20;
   static final byte isc_info_sql_stmt_type = 21;
   static final byte isc_info_sql_get_plan = 22;
   static final byte isc_info_sql_records = 23;
   static final byte isc_info_sql_batch_fetch = 24;
   static final byte isc_info_sql_precision = 25;
   static final int isc_info_sql_stmt_select = 1;
   static final int isc_info_sql_stmt_insert = 2;
   static final int isc_info_sql_stmt_update = 3;
   static final int isc_info_sql_stmt_delete = 4;
   static final int isc_info_sql_stmt_ddl = 5;
   static final int isc_info_sql_stmt_get_segment = 6;
   static final int isc_info_sql_stmt_put_segment = 7;
   static final int isc_info_sql_stmt_exec_procedure = 8;
   static final int isc_info_sql_stmt_start_trans = 9;
   static final int isc_info_sql_stmt_commit = 10;
   static final int isc_info_sql_stmt_rollback = 11;
   static final int isc_info_sql_stmt_select_for_upd = 12;
   static final int isc_info_sql_stmt_set_generator = 13;
   static final int isc_info_number_messages = 4;
   static final int isc_info_max_message = 5;
   static final int isc_info_max_send = 6;
   static final int isc_info_max_receive = 7;
   static final int isc_info_state = 8;
   static final int isc_info_message_number = 9;
   static final int isc_info_message_size = 10;
   static final int isc_info_request_cost = 11;
   static final int isc_info_access_path = 12;
   static final int isc_info_req_select_count = 13;
   static final int isc_info_req_insert_count = 14;
   static final int isc_info_req_update_count = 15;
   static final int isc_info_req_delete_count = 16;
   static final int isc_bpb_version1 = 1;
   static final int isc_bpb_source_type = 1;
   static final int isc_bpb_target_type = 2;
   static final int isc_bpb_type = 3;
   static final int isc_bpb_source_interp = 4;
   static final int isc_bpb_target_interp = 5;
   static final int isc_bpb_filter_parameter = 6;
   static final int isc_bpb_type_segmented = 0;
   static final int isc_bpb_type_stream = 1;
   static final int RBL_eof = 1;
   static final int RBL_segment = 2;
   static final int RBL_eof_pending = 4;
   static final int RBL_create = 8;
   static final int SUCCESS = 0;
   static final int isc_facility = 20;
   static final int isc_err_base = 335544320;
   static final int isc_err_factor = 1;
   static final int isc_arg_end = 0;
   static final int isc_arg_gds = 1;
   static final int isc_arg_string = 2;
   static final int isc_arg_cstring = 3;
   static final int isc_arg_number = 4;
   static final int isc_arg_interpreted = 5;
   static final int isc_arg_vms = 6;
   static final int isc_arg_unix = 7;
   static final int isc_arg_domain = 8;
   static final int isc_arg_dos = 9;
   static final int isc_arg_mpexl = 10;
   static final int isc_arg_mpexl_ipc = 11;
   static final int isc_arg_next_mach = 15;
   static final int isc_arg_netware = 16;
   static final int isc_arg_win32 = 17;
   static final int isc_arg_warning = 18;
   static final int isc_arith_except = 335544321;
   static final int isc_bad_dbkey = 335544322;
   static final int isc_bad_db_format = 335544323;
   static final int isc_bad_db_handle = 335544324;
   static final int isc_bad_dpb_content = 335544325;
   static final int isc_bad_dpb_form = 335544326;
   static final int isc_bad_req_handle = 335544327;
   static final int isc_bad_segstr_handle = 335544328;
   static final int isc_bad_segstr_id = 335544329;
   static final int isc_bad_tpb_content = 335544330;
   static final int isc_bad_tpb_form = 335544331;
   static final int isc_bad_trans_handle = 335544332;
   static final int isc_bug_check = 335544333;
   static final int isc_convert_error = 335544334;
   static final int isc_db_corrupt = 335544335;
   static final int isc_deadlock = 335544336;
   static final int isc_excess_trans = 335544337;
   static final int isc_from_no_match = 335544338;
   static final int isc_infinap = 335544339;
   static final int isc_infona = 335544340;
   static final int isc_infunk = 335544341;
   static final int isc_integ_fail = 335544342;
   static final int isc_invalid_blr = 335544343;
   static final int isc_io_error = 335544344;
   static final int isc_lock_conflict = 335544345;
   static final int isc_metadata_corrupt = 335544346;
   static final int isc_not_valid = 335544347;
   static final int isc_no_cur_rec = 335544348;
   static final int isc_no_dup = 335544349;
   static final int isc_no_finish = 335544350;
   static final int isc_no_meta_update = 335544351;
   static final int isc_no_priv = 335544352;
   static final int isc_no_recon = 335544353;
   static final int isc_no_record = 335544354;
   static final int isc_no_segstr_close = 335544355;
   static final int isc_obsolete_metadata = 335544356;
   static final int isc_open_trans = 335544357;
   static final int isc_port_len = 335544358;
   static final int isc_read_only_field = 335544359;
   static final int isc_read_only_rel = 335544360;
   static final int isc_read_only_trans = 335544361;
   static final int isc_read_only_view = 335544362;
   static final int isc_req_no_trans = 335544363;
   static final int isc_req_sync = 335544364;
   static final int isc_req_wrong_db = 335544365;
   static final int isc_segment = 335544366;
   static final int isc_segstr_eof = 335544367;
   static final int isc_segstr_no_op = 335544368;
   static final int isc_segstr_no_read = 335544369;
   static final int isc_segstr_no_trans = 335544370;
   static final int isc_segstr_no_write = 335544371;
   static final int isc_segstr_wrong_db = 335544372;
   static final int isc_sys_request = 335544373;
   static final int isc_stream_eof = 335544374;
   static final int isc_unavailable = 335544375;
   static final int isc_unres_rel = 335544376;
   static final int isc_uns_ext = 335544377;
   static final int isc_wish_list = 335544378;
   static final int isc_wrong_ods = 335544379;
   static final int isc_wronumarg = 335544380;
   static final int isc_imp_exc = 335544381;
   static final int isc_random = 335544382;
   static final int isc_fatal_conflict = 335544383;
   static final int isc_badblk = 335544384;
   static final int isc_invpoolcl = 335544385;
   static final int isc_nopoolids = 335544386;
   static final int isc_relbadblk = 335544387;
   static final int isc_blktoobig = 335544388;
   static final int isc_bufexh = 335544389;
   static final int isc_syntaxerr = 335544390;
   static final int isc_bufinuse = 335544391;
   static final int isc_bdbincon = 335544392;
   static final int isc_reqinuse = 335544393;
   static final int isc_badodsver = 335544394;
   static final int isc_relnotdef = 335544395;
   static final int isc_fldnotdef = 335544396;
   static final int isc_dirtypage = 335544397;
   static final int isc_waifortra = 335544398;
   static final int isc_doubleloc = 335544399;
   static final int isc_nodnotfnd = 335544400;
   static final int isc_dupnodfnd = 335544401;
   static final int isc_locnotmar = 335544402;
   static final int isc_badpagtyp = 335544403;
   static final int isc_corrupt = 335544404;
   static final int isc_badpage = 335544405;
   static final int isc_badindex = 335544406;
   static final int isc_dbbnotzer = 335544407;
   static final int isc_tranotzer = 335544408;
   static final int isc_trareqmis = 335544409;
   static final int isc_badhndcnt = 335544410;
   static final int isc_wrotpbver = 335544411;
   static final int isc_wroblrver = 335544412;
   static final int isc_wrodpbver = 335544413;
   static final int isc_blobnotsup = 335544414;
   static final int isc_badrelation = 335544415;
   static final int isc_nodetach = 335544416;
   static final int isc_notremote = 335544417;
   static final int isc_trainlim = 335544418;
   static final int isc_notinlim = 335544419;
   static final int isc_traoutsta = 335544420;
   static final int isc_connect_reject = 335544421;
   static final int isc_dbfile = 335544422;
   static final int isc_orphan = 335544423;
   static final int isc_no_lock_mgr = 335544424;
   static final int isc_ctxinuse = 335544425;
   static final int isc_ctxnotdef = 335544426;
   static final int isc_datnotsup = 335544427;
   static final int isc_badmsgnum = 335544428;
   static final int isc_badparnum = 335544429;
   static final int isc_virmemexh = 335544430;
   static final int isc_blocking_signal = 335544431;
   static final int isc_lockmanerr = 335544432;
   static final int isc_journerr = 335544433;
   static final int isc_keytoobig = 335544434;
   static final int isc_nullsegkey = 335544435;
   static final int isc_sqlerr = 335544436;
   static final int isc_wrodynver = 335544437;
   static final int isc_funnotdef = 335544438;
   static final int isc_funmismat = 335544439;
   static final int isc_bad_msg_vec = 335544440;
   static final int isc_bad_detach = 335544441;
   static final int isc_noargacc_read = 335544442;
   static final int isc_noargacc_write = 335544443;
   static final int isc_read_only = 335544444;
   static final int isc_ext_err = 335544445;
   static final int isc_non_updatable = 335544446;
   static final int isc_no_rollback = 335544447;
   static final int isc_bad_sec_info = 335544448;
   static final int isc_invalid_sec_info = 335544449;
   static final int isc_misc_interpreted = 335544450;
   static final int isc_update_conflict = 335544451;
   static final int isc_unlicensed = 335544452;
   static final int isc_obj_in_use = 335544453;
   static final int isc_nofilter = 335544454;
   static final int isc_shadow_accessed = 335544455;
   static final int isc_invalid_sdl = 335544456;
   static final int isc_out_of_bounds = 335544457;
   static final int isc_invalid_dimension = 335544458;
   static final int isc_rec_in_limbo = 335544459;
   static final int isc_shadow_missing = 335544460;
   static final int isc_cant_validate = 335544461;
   static final int isc_cant_start_journal = 335544462;
   static final int isc_gennotdef = 335544463;
   static final int isc_cant_start_logging = 335544464;
   static final int isc_bad_segstr_type = 335544465;
   static final int isc_foreign_key = 335544466;
   static final int isc_high_minor = 335544467;
   static final int isc_tra_state = 335544468;
   static final int isc_trans_invalid = 335544469;
   static final int isc_buf_invalid = 335544470;
   static final int isc_indexnotdefined = 335544471;
   static final int isc_login = 335544472;
   static final int isc_invalid_bookmark = 335544473;
   static final int isc_bad_lock_level = 335544474;
   static final int isc_relation_lock = 335544475;
   static final int isc_record_lock = 335544476;
   static final int isc_max_idx = 335544477;
   static final int isc_jrn_enable = 335544478;
   static final int isc_old_failure = 335544479;
   static final int isc_old_in_progress = 335544480;
   static final int isc_old_no_space = 335544481;
   static final int isc_no_wal_no_jrn = 335544482;
   static final int isc_num_old_files = 335544483;
   static final int isc_wal_file_open = 335544484;
   static final int isc_bad_stmt_handle = 335544485;
   static final int isc_wal_failure = 335544486;
   static final int isc_walw_err = 335544487;
   static final int isc_logh_small = 335544488;
   static final int isc_logh_inv_version = 335544489;
   static final int isc_logh_open_flag = 335544490;
   static final int isc_logh_open_flag2 = 335544491;
   static final int isc_logh_diff_dbname = 335544492;
   static final int isc_logf_unexpected_eof = 335544493;
   static final int isc_logr_incomplete = 335544494;
   static final int isc_logr_header_small = 335544495;
   static final int isc_logb_small = 335544496;
   static final int isc_wal_illegal_attach = 335544497;
   static final int isc_wal_invalid_wpb = 335544498;
   static final int isc_wal_err_rollover = 335544499;
   static final int isc_no_wal = 335544500;
   static final int isc_drop_wal = 335544501;
   static final int isc_stream_not_defined = 335544502;
   static final int isc_wal_subsys_error = 335544503;
   static final int isc_wal_subsys_corrupt = 335544504;
   static final int isc_no_archive = 335544505;
   static final int isc_shutinprog = 335544506;
   static final int isc_range_in_use = 335544507;
   static final int isc_range_not_found = 335544508;
   static final int isc_charset_not_found = 335544509;
   static final int isc_lock_timeout = 335544510;
   static final int isc_prcnotdef = 335544511;
   static final int isc_prcmismat = 335544512;
   static final int isc_wal_bugcheck = 335544513;
   static final int isc_wal_cant_expand = 335544514;
   static final int isc_codnotdef = 335544515;
   static final int isc_xcpnotdef = 335544516;
   static final int isc_except = 335544517;
   static final int isc_cache_restart = 335544518;
   static final int isc_bad_lock_handle = 335544519;
   static final int isc_jrn_present = 335544520;
   static final int isc_wal_err_rollover2 = 335544521;
   static final int isc_wal_err_logwrite = 335544522;
   static final int isc_wal_err_jrn_comm = 335544523;
   static final int isc_wal_err_expansion = 335544524;
   static final int isc_wal_err_setup = 335544525;
   static final int isc_wal_err_ww_sync = 335544526;
   static final int isc_wal_err_ww_start = 335544527;
   static final int isc_shutdown = 335544528;
   static final int isc_existing_priv_mod = 335544529;
   static final int isc_primary_key_ref = 335544530;
   static final int isc_primary_key_notnull = 335544531;
   static final int isc_ref_cnstrnt_notfound = 335544532;
   static final int isc_foreign_key_notfound = 335544533;
   static final int isc_ref_cnstrnt_update = 335544534;
   static final int isc_check_cnstrnt_update = 335544535;
   static final int isc_check_cnstrnt_del = 335544536;
   static final int isc_integ_index_seg_del = 335544537;
   static final int isc_integ_index_seg_mod = 335544538;
   static final int isc_integ_index_del = 335544539;
   static final int isc_integ_index_mod = 335544540;
   static final int isc_check_trig_del = 335544541;
   static final int isc_check_trig_update = 335544542;
   static final int isc_cnstrnt_fld_del = 335544543;
   static final int isc_cnstrnt_fld_rename = 335544544;
   static final int isc_rel_cnstrnt_update = 335544545;
   static final int isc_constaint_on_view = 335544546;
   static final int isc_invld_cnstrnt_type = 335544547;
   static final int isc_primary_key_exists = 335544548;
   static final int isc_systrig_update = 335544549;
   static final int isc_not_rel_owner = 335544550;
   static final int isc_grant_obj_notfound = 335544551;
   static final int isc_grant_fld_notfound = 335544552;
   static final int isc_grant_nopriv = 335544553;
   static final int isc_nonsql_security_rel = 335544554;
   static final int isc_nonsql_security_fld = 335544555;
   static final int isc_wal_cache_err = 335544556;
   static final int isc_shutfail = 335544557;
   static final int isc_check_constraint = 335544558;
   static final int isc_bad_svc_handle = 335544559;
   static final int isc_shutwarn = 335544560;
   static final int isc_wrospbver = 335544561;
   static final int isc_bad_spb_form = 335544562;
   static final int isc_svcnotdef = 335544563;
   static final int isc_no_jrn = 335544564;
   static final int isc_transliteration_failed = 335544565;
   static final int isc_start_cm_for_wal = 335544566;
   static final int isc_wal_ovflow_log_required = 335544567;
   static final int isc_text_subtype = 335544568;
   static final int isc_dsql_error = 335544569;
   static final int isc_dsql_command_err = 335544570;
   static final int isc_dsql_constant_err = 335544571;
   static final int isc_dsql_cursor_err = 335544572;
   static final int isc_dsql_datatype_err = 335544573;
   static final int isc_dsql_decl_err = 335544574;
   static final int isc_dsql_cursor_update_err = 335544575;
   static final int isc_dsql_cursor_open_err = 335544576;
   static final int isc_dsql_cursor_close_err = 335544577;
   static final int isc_dsql_field_err = 335544578;
   static final int isc_dsql_internal_err = 335544579;
   static final int isc_dsql_relation_err = 335544580;
   static final int isc_dsql_procedure_err = 335544581;
   static final int isc_dsql_request_err = 335544582;
   static final int isc_dsql_sqlda_err = 335544583;
   static final int isc_dsql_var_count_err = 335544584;
   static final int isc_dsql_stmt_handle = 335544585;
   static final int isc_dsql_function_err = 335544586;
   static final int isc_dsql_blob_err = 335544587;
   static final int isc_collation_not_found = 335544588;
   static final int isc_collation_not_for_charset = 335544589;
   static final int isc_dsql_dup_option = 335544590;
   static final int isc_dsql_tran_err = 335544591;
   static final int isc_dsql_invalid_array = 335544592;
   static final int isc_dsql_max_arr_dim_exceeded = 335544593;
   static final int isc_dsql_arr_range_error = 335544594;
   static final int isc_dsql_trigger_err = 335544595;
   static final int isc_dsql_subselect_err = 335544596;
   static final int isc_dsql_crdb_prepare_err = 335544597;
   static final int isc_specify_field_err = 335544598;
   static final int isc_num_field_err = 335544599;
   static final int isc_col_name_err = 335544600;
   static final int isc_where_err = 335544601;
   static final int isc_table_view_err = 335544602;
   static final int isc_distinct_err = 335544603;
   static final int isc_key_field_count_err = 335544604;
   static final int isc_subquery_err = 335544605;
   static final int isc_expression_eval_err = 335544606;
   static final int isc_node_err = 335544607;
   static final int isc_command_end_err = 335544608;
   static final int isc_index_name = 335544609;
   static final int isc_exception_name = 335544610;
   static final int isc_field_name = 335544611;
   static final int isc_token_err = 335544612;
   static final int isc_union_err = 335544613;
   static final int isc_dsql_construct_err = 335544614;
   static final int isc_field_aggregate_err = 335544615;
   static final int isc_field_ref_err = 335544616;
   static final int isc_order_by_err = 335544617;
   static final int isc_return_mode_err = 335544618;
   static final int isc_extern_func_err = 335544619;
   static final int isc_alias_conflict_err = 335544620;
   static final int isc_procedure_conflict_error = 335544621;
   static final int isc_relation_conflict_err = 335544622;
   static final int isc_dsql_domain_err = 335544623;
   static final int isc_idx_seg_err = 335544624;
   static final int isc_node_name_err = 335544625;
   static final int isc_table_name = 335544626;
   static final int isc_proc_name = 335544627;
   static final int isc_idx_create_err = 335544628;
   static final int isc_wal_shadow_err = 335544629;
   static final int isc_dependency = 335544630;
   static final int isc_idx_key_err = 335544631;
   static final int isc_dsql_file_length_err = 335544632;
   static final int isc_dsql_shadow_number_err = 335544633;
   static final int isc_dsql_token_unk_err = 335544634;
   static final int isc_dsql_no_relation_alias = 335544635;
   static final int isc_indexname = 335544636;
   static final int isc_no_stream_plan = 335544637;
   static final int isc_stream_twice = 335544638;
   static final int isc_stream_not_found = 335544639;
   static final int isc_collation_requires_text = 335544640;
   static final int isc_dsql_domain_not_found = 335544641;
   static final int isc_index_unused = 335544642;
   static final int isc_dsql_self_join = 335544643;
   static final int isc_stream_bof = 335544644;
   static final int isc_stream_crack = 335544645;
   static final int isc_db_or_file_exists = 335544646;
   static final int isc_invalid_operator = 335544647;
   static final int isc_conn_lost = 335544648;
   static final int isc_bad_checksum = 335544649;
   static final int isc_page_type_err = 335544650;
   static final int isc_ext_readonly_err = 335544651;
   static final int isc_sing_select_err = 335544652;
   static final int isc_psw_attach = 335544653;
   static final int isc_psw_start_trans = 335544654;
   static final int isc_invalid_direction = 335544655;
   static final int isc_dsql_var_conflict = 335544656;
   static final int isc_dsql_no_blob_array = 335544657;
   static final int isc_dsql_base_table = 335544658;
   static final int isc_duplicate_base_table = 335544659;
   static final int isc_view_alias = 335544660;
   static final int isc_index_root_page_full = 335544661;
   static final int isc_dsql_blob_type_unknown = 335544662;
   static final int isc_req_max_clones_exceeded = 335544663;
   static final int isc_dsql_duplicate_spec = 335544664;
   static final int isc_unique_key_violation = 335544665;
   static final int isc_srvr_version_too_old = 335544666;
   static final int isc_drdb_completed_with_errs = 335544667;
   static final int isc_dsql_procedure_use_err = 335544668;
   static final int isc_dsql_count_mismatch = 335544669;
   static final int isc_blob_idx_err = 335544670;
   static final int isc_array_idx_err = 335544671;
   static final int isc_key_field_err = 335544672;
   static final int isc_no_delete = 335544673;
   static final int isc_del_last_field = 335544674;
   static final int isc_sort_err = 335544675;
   static final int isc_sort_mem_err = 335544676;
   static final int isc_version_err = 335544677;
   static final int isc_inval_key_posn = 335544678;
   static final int isc_no_segments_err = 335544679;
   static final int isc_crrp_data_err = 335544680;
   static final int isc_rec_size_err = 335544681;
   static final int isc_dsql_field_ref = 335544682;
   static final int isc_req_depth_exceeded = 335544683;
   static final int isc_no_field_access = 335544684;
   static final int isc_no_dbkey = 335544685;
   static final int isc_jrn_format_err = 335544686;
   static final int isc_jrn_file_full = 335544687;
   static final int isc_dsql_open_cursor_request = 335544688;
   static final int isc_ib_error = 335544689;
   static final int isc_cache_redef = 335544690;
   static final int isc_cache_too_small = 335544691;
   static final int isc_log_redef = 335544692;
   static final int isc_log_too_small = 335544693;
   static final int isc_partition_too_small = 335544694;
   static final int isc_partition_not_supp = 335544695;
   static final int isc_log_length_spec = 335544696;
   static final int isc_precision_err = 335544697;
   static final int isc_scale_nogt = 335544698;
   static final int isc_expec_int = 335544699;
   static final int isc_expec_long = 335544700;
   static final int isc_expec_uint = 335544701;
   static final int isc_like_escape_invalid = 335544702;
   static final int isc_svcnoexe = 335544703;
   static final int isc_net_lookup_err = 335544704;
   static final int isc_service_unknown = 335544705;
   static final int isc_host_unknown = 335544706;
   static final int isc_grant_nopriv_on_base = 335544707;
   static final int isc_dyn_fld_ambiguous = 335544708;
   static final int isc_dsql_agg_ref_err = 335544709;
   static final int isc_complex_view = 335544710;
   static final int isc_unprepared_stmt = 335544711;
   static final int isc_expec_positive = 335544712;
   static final int isc_dsql_sqlda_value_err = 335544713;
   static final int isc_invalid_array_id = 335544714;
   static final int isc_extfile_uns_op = 335544715;
   static final int isc_svc_in_use = 335544716;
   static final int isc_err_stack_limit = 335544717;
   static final int isc_invalid_key = 335544718;
   static final int isc_net_init_error = 335544719;
   static final int isc_loadlib_failure = 335544720;
   static final int isc_network_error = 335544721;
   static final int isc_net_connect_err = 335544722;
   static final int isc_net_connect_listen_err = 335544723;
   static final int isc_net_event_connect_err = 335544724;
   static final int isc_net_event_listen_err = 335544725;
   static final int isc_net_read_err = 335544726;
   static final int isc_net_write_err = 335544727;
   static final int isc_integ_index_deactivate = 335544728;
   static final int isc_integ_deactivate_primary = 335544729;
   static final int isc_cse_not_supported = 335544730;
   static final int isc_tra_must_sweep = 335544731;
   static final int isc_unsupported_network_drive = 335544732;
   static final int isc_io_create_err = 335544733;
   static final int isc_io_open_err = 335544734;
   static final int isc_io_close_err = 335544735;
   static final int isc_io_read_err = 335544736;
   static final int isc_io_write_err = 335544737;
   static final int isc_io_delete_err = 335544738;
   static final int isc_io_access_err = 335544739;
   static final int isc_udf_exception = 335544740;
   static final int isc_lost_db_connection = 335544741;
   static final int isc_no_write_user_priv = 335544742;
   static final int isc_token_too_long = 335544743;
   static final int isc_max_att_exceeded = 335544744;
   static final int isc_login_same_as_role_name = 335544745;
   static final int isc_reftable_requires_pk = 335544746;
   static final int isc_usrname_too_long = 335544747;
   static final int isc_password_too_long = 335544748;
   static final int isc_usrname_required = 335544749;
   static final int isc_password_required = 335544750;
   static final int isc_bad_protocol = 335544751;
   static final int isc_dup_usrname_found = 335544752;
   static final int isc_usrname_not_found = 335544753;
   static final int isc_error_adding_sec_record = 335544754;
   static final int isc_error_modifying_sec_record = 335544755;
   static final int isc_error_deleting_sec_record = 335544756;
   static final int isc_error_updating_sec_db = 335544757;
   static final int isc_sort_rec_size_err = 335544758;
   static final int isc_bad_default_value = 335544759;
   static final int isc_invalid_clause = 335544760;
   static final int isc_too_many_handles = 335544761;
   static final int isc_optimizer_blk_exc = 335544762;
   static final int isc_invalid_string_constant = 335544763;
   static final int isc_transitional_date = 335544764;
   static final int isc_read_only_database = 335544765;
   static final int isc_must_be_dialect_2_and_up = 335544766;
   static final int isc_blob_filter_exception = 335544767;
   static final int isc_exception_access_violation = 335544768;
   static final int isc_exception_datatype_missalignment = 335544769;
   static final int isc_exception_array_bounds_exceeded = 335544770;
   static final int isc_exception_float_denormal_operand = 335544771;
   static final int isc_exception_float_divide_by_zero = 335544772;
   static final int isc_exception_float_inexact_result = 335544773;
   static final int isc_exception_float_invalid_operand = 335544774;
   static final int isc_exception_float_overflow = 335544775;
   static final int isc_exception_float_stack_check = 335544776;
   static final int isc_exception_float_underflow = 335544777;
   static final int isc_exception_integer_divide_by_zero = 335544778;
   static final int isc_exception_integer_overflow = 335544779;
   static final int isc_exception_unknown = 335544780;
   static final int isc_exception_stack_overflow = 335544781;
   static final int isc_exception_sigsegv = 335544782;
   static final int isc_exception_sigill = 335544783;
   static final int isc_exception_sigbus = 335544784;
   static final int isc_exception_sigfpe = 335544785;
   static final int isc_ext_file_delete = 335544786;
   static final int isc_ext_file_modify = 335544787;
   static final int isc_adm_task_denied = 335544788;
   static final int isc_extract_input_mismatch = 335544789;
   static final int isc_insufficient_svc_privileges = 335544790;
   static final int isc_file_in_use = 335544791;
   static final int isc_service_att_err = 335544792;
   static final int isc_ddl_not_allowed_by_db_sql_dial = 335544793;
   static final int isc_cancelled = 335544794;
   static final int isc_unexp_spb_form = 335544795;
   static final int isc_sql_dialect_datatype_unsupport = 335544796;
   static final int isc_svcnouser = 335544797;
   static final int isc_depend_on_uncommitted_rel = 335544798;
   static final int isc_svc_name_missing = 335544799;
   static final int isc_too_many_contexts = 335544800;
   static final int isc_datype_notsup = 335544801;
   static final int isc_dialect_reset_warning = 335544802;
   static final int isc_dialect_not_changed = 335544803;
   static final int isc_database_create_failed = 335544804;
   static final int isc_inv_dialect_specified = 335544805;
   static final int isc_valid_db_dialects = 335544806;
   static final int isc_sqlwarn = 335544807;
   static final int isc_dtype_renamed = 335544808;
   static final int isc_extern_func_dir_error = 335544809;
   static final int isc_date_range_exceeded = 335544810;
   static final int isc_inv_client_dialect_specified = 335544811;
   static final int isc_valid_client_dialects = 335544812;
   static final int isc_optimizer_between_err = 335544813;
   static final int isc_service_not_supported = 335544814;
   static final int isc_gfix_db_name = 335740929;
   static final int isc_gfix_invalid_sw = 335740930;
   static final int isc_gfix_incmp_sw = 335740932;
   static final int isc_gfix_replay_req = 335740933;
   static final int isc_gfix_pgbuf_req = 335740934;
   static final int isc_gfix_val_req = 335740935;
   static final int isc_gfix_pval_req = 335740936;
   static final int isc_gfix_trn_req = 335740937;
   static final int isc_gfix_full_req = 335740940;
   static final int isc_gfix_usrname_req = 335740941;
   static final int isc_gfix_pass_req = 335740942;
   static final int isc_gfix_subs_name = 335740943;
   static final int isc_gfix_wal_req = 335740944;
   static final int isc_gfix_sec_req = 335740945;
   static final int isc_gfix_nval_req = 335740946;
   static final int isc_gfix_type_shut = 335740947;
   static final int isc_gfix_retry = 335740948;
   static final int isc_gfix_retry_db = 335740951;
   static final int isc_gfix_exceed_max = 335740991;
   static final int isc_gfix_corrupt_pool = 335740992;
   static final int isc_gfix_mem_exhausted = 335740993;
   static final int isc_gfix_bad_pool = 335740994;
   static final int isc_gfix_trn_not_valid = 335740995;
   static final int isc_gfix_unexp_eoi = 335741012;
   static final int isc_gfix_recon_fail = 335741018;
   static final int isc_gfix_trn_unknown = 335741036;
   static final int isc_gfix_mode_req = 335741038;
   static final int isc_gfix_opt_SQL_dialect = 335741039;
   static final int isc_dsql_dbkey_from_non_table = 336003074;
   static final int isc_dsql_transitional_numeric = 336003075;
   static final int isc_dsql_dialect_warning_expr = 336003076;
   static final int isc_sql_db_dialect_dtype_unsupport = 336003077;
   static final int isc_isc_sql_dialect_conflict_num = 336003079;
   static final int isc_dsql_warning_number_ambiguous = 336003080;
   static final int isc_dsql_warning_number_ambiguous1 = 336003081;
   static final int isc_dsql_warn_precision_ambiguous = 336003082;
   static final int isc_dsql_warn_precision_ambiguous1 = 336003083;
   static final int isc_dsql_warn_precision_ambiguous2 = 336003084;
   static final int isc_dsql_rows_ties_err = 336003085;
   static final int isc_dyn_role_does_not_exist = 336068796;
   static final int isc_dyn_no_grant_admin_opt = 336068797;
   static final int isc_dyn_user_not_role_member = 336068798;
   static final int isc_dyn_delete_role_failed = 336068799;
   static final int isc_dyn_grant_role_to_user = 336068800;
   static final int isc_dyn_inv_sql_role_name = 336068801;
   static final int isc_dyn_dup_sql_role = 336068802;
   static final int isc_dyn_kywd_spec_for_role = 336068803;
   static final int isc_dyn_roles_not_supported = 336068804;
   static final int isc_dyn_domain_name_exists = 336068812;
   static final int isc_dyn_field_name_exists = 336068813;
   static final int isc_dyn_dependency_exists = 336068814;
   static final int isc_dyn_dtype_invalid = 336068815;
   static final int isc_dyn_char_fld_too_small = 336068816;
   static final int isc_dyn_invalid_dtype_conversion = 336068817;
   static final int isc_dyn_dtype_conv_invalid = 336068818;
   static final int isc_gbak_unknown_switch = 336330753;
   static final int isc_gbak_page_size_missing = 336330754;
   static final int isc_gbak_page_size_toobig = 336330755;
   static final int isc_gbak_redir_ouput_missing = 336330756;
   static final int isc_gbak_switches_conflict = 336330757;
   static final int isc_gbak_unknown_device = 336330758;
   static final int isc_gbak_no_protection = 336330759;
   static final int isc_gbak_page_size_not_allowed = 336330760;
   static final int isc_gbak_multi_source_dest = 336330761;
   static final int isc_gbak_filename_missing = 336330762;
   static final int isc_gbak_dup_inout_names = 336330763;
   static final int isc_gbak_inv_page_size = 336330764;
   static final int isc_gbak_db_specified = 336330765;
   static final int isc_gbak_db_exists = 336330766;
   static final int isc_gbak_unk_device = 336330767;
   static final int isc_gbak_blob_info_failed = 336330772;
   static final int isc_gbak_unk_blob_item = 336330773;
   static final int isc_gbak_get_seg_failed = 336330774;
   static final int isc_gbak_close_blob_failed = 336330775;
   static final int isc_gbak_open_blob_failed = 336330776;
   static final int isc_gbak_put_blr_gen_id_failed = 336330777;
   static final int isc_gbak_unk_type = 336330778;
   static final int isc_gbak_comp_req_failed = 336330779;
   static final int isc_gbak_start_req_failed = 336330780;
   static final int isc_gbak_rec_failed = 336330781;
   static final int isc_gbak_rel_req_failed = 336330782;
   static final int isc_gbak_db_info_failed = 336330783;
   static final int isc_gbak_no_db_desc = 336330784;
   static final int isc_gbak_db_create_failed = 336330785;
   static final int isc_gbak_decomp_len_error = 336330786;
   static final int isc_gbak_tbl_missing = 336330787;
   static final int isc_gbak_blob_col_missing = 336330788;
   static final int isc_gbak_create_blob_failed = 336330789;
   static final int isc_gbak_put_seg_failed = 336330790;
   static final int isc_gbak_rec_len_exp = 336330791;
   static final int isc_gbak_inv_rec_len = 336330792;
   static final int isc_gbak_exp_data_type = 336330793;
   static final int isc_gbak_gen_id_failed = 336330794;
   static final int isc_gbak_unk_rec_type = 336330795;
   static final int isc_gbak_inv_bkup_ver = 336330796;
   static final int isc_gbak_missing_bkup_desc = 336330797;
   static final int isc_gbak_string_trunc = 336330798;
   static final int isc_gbak_cant_rest_record = 336330799;
   static final int isc_gbak_send_failed = 336330800;
   static final int isc_gbak_no_tbl_name = 336330801;
   static final int isc_gbak_unexp_eof = 336330802;
   static final int isc_gbak_db_format_too_old = 336330803;
   static final int isc_gbak_inv_array_dim = 336330804;
   static final int isc_gbak_xdr_len_expected = 336330807;
   static final int isc_gbak_open_bkup_error = 336330817;
   static final int isc_gbak_open_error = 336330818;
   static final int isc_gbak_missing_block_fac = 336330934;
   static final int isc_gbak_inv_block_fac = 336330935;
   static final int isc_gbak_block_fac_specified = 336330936;
   static final int isc_gbak_missing_username = 336330940;
   static final int isc_gbak_missing_password = 336330941;
   static final int isc_gbak_missing_skipped_bytes = 336330952;
   static final int isc_gbak_inv_skipped_bytes = 336330953;
   static final int isc_gbak_err_restore_charset = 336330965;
   static final int isc_gbak_err_restore_collation = 336330967;
   static final int isc_gbak_read_error = 336330972;
   static final int isc_gbak_write_error = 336330973;
   static final int isc_gbak_db_in_use = 336330985;
   static final int isc_gbak_sysmemex = 336330990;
   static final int isc_gbak_restore_role_failed = 336331002;
   static final int isc_gbak_role_op_missing = 336331005;
   static final int isc_gbak_page_buffers_missing = 336331010;
   static final int isc_gbak_page_buffers_wrong_param = 336331011;
   static final int isc_gbak_page_buffers_restore = 336331012;
   static final int isc_gbak_inv_size = 336331014;
   static final int isc_gbak_file_outof_sequence = 336331015;
   static final int isc_gbak_join_file_missing = 336331016;
   static final int isc_gbak_stdin_not_supptd = 336331017;
   static final int isc_gbak_stdout_not_supptd = 336331018;
   static final int isc_gbak_bkup_corrupt = 336331019;
   static final int isc_gbak_unk_db_file_spec = 336331020;
   static final int isc_gbak_hdr_write_failed = 336331021;
   static final int isc_gbak_disk_space_ex = 336331022;
   static final int isc_gbak_size_lt_min = 336331023;
   static final int isc_gbak_svc_name_missing = 336331025;
   static final int isc_gbak_not_ownr = 336331026;
   static final int isc_gbak_mode_req = 336331031;
   static final int isc_gsec_cant_open_db = 336723983;
   static final int isc_gsec_switches_error = 336723984;
   static final int isc_gsec_no_op_spec = 336723985;
   static final int isc_gsec_no_usr_name = 336723986;
   static final int isc_gsec_err_add = 336723987;
   static final int isc_gsec_err_modify = 336723988;
   static final int isc_gsec_err_find_mod = 336723989;
   static final int isc_gsec_err_rec_not_found = 336723990;
   static final int isc_gsec_err_delete = 336723991;
   static final int isc_gsec_err_find_del = 336723992;
   static final int isc_gsec_err_find_disp = 336723996;
   static final int isc_gsec_inv_param = 336723997;
   static final int isc_gsec_op_specified = 336723998;
   static final int isc_gsec_pw_specified = 336723999;
   static final int isc_gsec_uid_specified = 336724000;
   static final int isc_gsec_gid_specified = 336724001;
   static final int isc_gsec_proj_specified = 336724002;
   static final int isc_gsec_org_specified = 336724003;
   static final int isc_gsec_fname_specified = 336724004;
   static final int isc_gsec_mname_specified = 336724005;
   static final int isc_gsec_lname_specified = 336724006;
   static final int isc_gsec_inv_switch = 336724008;
   static final int isc_gsec_amb_switch = 336724009;
   static final int isc_gsec_no_op_specified = 336724010;
   static final int isc_gsec_params_not_allowed = 336724011;
   static final int isc_gsec_incompat_switch = 336724012;
   static final int isc_gsec_inv_username = 336724044;
   static final int isc_gsec_inv_pw_length = 336724045;
   static final int isc_gsec_db_specified = 336724046;
   static final int isc_gsec_db_admin_specified = 336724047;
   static final int isc_gsec_db_admin_pw_specified = 336724048;
   static final int isc_gsec_sql_role_specified = 336724049;
   static final int isc_license_no_file = 336789504;
   static final int isc_license_op_specified = 336789523;
   static final int isc_license_op_missing = 336789524;
   static final int isc_license_inv_switch = 336789525;
   static final int isc_license_inv_switch_combo = 336789526;
   static final int isc_license_inv_op_combo = 336789527;
   static final int isc_license_amb_switch = 336789528;
   static final int isc_license_inv_parameter = 336789529;
   static final int isc_license_param_specified = 336789530;
   static final int isc_license_param_req = 336789531;
   static final int isc_license_syntx_error = 336789532;
   static final int isc_license_dup_id = 336789534;
   static final int isc_license_inv_id_key = 336789535;
   static final int isc_license_err_remove = 336789536;
   static final int isc_license_err_update = 336789537;
   static final int isc_license_err_convert = 336789538;
   static final int isc_license_err_unk = 336789539;
   static final int isc_license_svc_err_add = 336789540;
   static final int isc_license_svc_err_remove = 336789541;
   static final int isc_license_eval_exists = 336789563;
   static final int isc_gstat_unknown_switch = 336920577;
   static final int isc_gstat_retry = 336920578;
   static final int isc_gstat_wrong_ods = 336920579;
   static final int isc_gstat_unexpected_eof = 336920580;
   static final int isc_gstat_open_err = 336920605;
   static final int isc_gstat_read_err = 336920606;
   static final int isc_gstat_sysmemex = 336920607;
   static final int isc_err_max = 690;
   static final int SQL_TEXT = 452;
   static final int SQL_VARYING = 448;
   static final int SQL_SHORT = 500;
   static final int SQL_LONG = 496;
   static final int SQL_FLOAT = 482;
   static final int SQL_DOUBLE = 480;
   static final int SQL_D_FLOAT = 530;
   static final int SQL_TIMESTAMP = 510;
   static final int SQL_BLOB = 520;
   static final int SQL_ARRAY = 540;
   static final int SQL_QUAD = 550;
   static final int SQL_TYPE_TIME = 560;
   static final int SQL_TYPE_DATE = 570;
   static final int SQL_INT64 = 580;
   static final int SQL_BOOLEAN = 590;
   static final int SQL_VARYING_STRING = 600;
   static final int SQL_DATE = 510;
   static final int MAX_BUFFER_SIZE = 1024;
   static final int MAX_FETCH_ROWS = 200;
   static final int op_void = 0;
   static final int op_connect = 1;
   static final int op_exit = 2;
   static final int op_accept = 3;
   static final int op_reject = 4;
   static final int op_protocol = 5;
   static final int op_disconnect = 6;
   static final int op_credit = 7;
   static final int op_continuation = 8;
   static final int op_response = 9;
   static final int op_open_file = 10;
   static final int op_create_file = 11;
   static final int op_close_file = 12;
   static final int op_read_page = 13;
   static final int op_write_page = 14;
   static final int op_lock = 15;
   static final int op_convert_lock = 16;
   static final int op_release_lock = 17;
   static final int op_blocking = 18;
   static final int op_attach = 19;
   static final int op_create = 20;
   static final int op_detach = 21;
   static final int op_compile = 22;
   static final int op_start = 23;
   static final int op_start_and_send = 24;
   static final int op_send = 25;
   static final int op_receive = 26;
   static final int op_unwind = 27;
   static final int op_release = 28;
   static final int op_transaction = 29;
   static final int op_commit = 30;
   static final int op_rollback = 31;
   static final int op_prepare = 32;
   static final int op_reconnect = 33;
   static final int op_create_blob = 34;
   static final int op_open_blob = 35;
   static final int op_get_segment = 36;
   static final int op_put_segment = 37;
   static final int op_cancel_blob = 38;
   static final int op_close_blob = 39;
   static final int op_info_database = 40;
   static final int op_info_request = 41;
   static final int op_info_transaction = 42;
   static final int op_info_blob = 43;
   static final int op_batch_segments = 44;
   static final int op_mgr_set_affinity = 45;
   static final int op_mgr_clear_affinity = 46;
   static final int op_mgr_report = 47;
   static final int op_que_events = 48;
   static final int op_cancel_events = 49;
   static final int op_commit_retaining = 50;
   static final int op_prepare2 = 51;
   static final int op_event = 52;
   static final int op_connect_request = 53;
   static final int op_aux_connect = 54;
   static final int op_ddl = 55;
   static final int op_open_blob2 = 56;
   static final int op_create_blob2 = 57;
   static final int op_get_slice = 58;
   static final int op_put_slice = 59;
   static final int op_slice = 60;
   static final int op_seek_blob = 61;
   static final int op_allocate_statement = 62;
   static final int op_execute = 63;
   static final int op_exec_immediate = 64;
   static final int op_fetch = 65;
   static final int op_fetch_response = 66;
   static final int op_free_statement = 67;
   static final int op_prepare_statement = 68;
   static final int op_set_cursor = 69;
   static final int op_info_sql = 70;
   static final int op_dummy = 71;
   static final int op_response_piggyback = 72;
   static final int op_start_and_receive = 73;
   static final int op_start_send_and_receive = 74;
   static final int op_exec_immediate2 = 75;
   static final int op_execute2 = 76;
   static final int op_insert = 77;
   static final int op_sql_response = 78;
   static final int op_transact = 79;
   static final int op_transact_response = 80;
   static final int op_drop_database = 81;
   static final int op_service_attach = 82;
   static final int op_service_detach = 83;
   static final int op_service_info = 84;
   static final int op_service_start = 85;
   static final int op_rollback_retaining = 86;
   static final int op_rollback_shutdown = 87;
   static final int op_release_savepoint = 88;
   static final int op_rollback_savepoint = 89;
   static final int op_start_savepoint = 90;
   static final int op_route = 91;
   static final byte blr_text = 14;
   static final byte blr_text2 = 15;
   static final byte blr_short = 7;
   static final byte blr_long = 8;
   static final byte blr_quad = 9;
   static final byte blr_int64 = 16;
   static final byte blr_float = 10;
   static final byte blr_double = 27;
   static final byte blr_d_float = 11;
   static final byte blr_timestamp = 35;
   static final byte blr_varying = 37;
   static final byte blr_varying2 = 38;
   static final int blr_blob = 261;
   static final byte blr_cstring = 40;
   static final byte blr_cstring2 = 41;
   static final byte blr_blob_id = 45;
   static final byte blr_sql_date = 12;
   static final byte blr_sql_time = 13;
   static final byte blr_boolean_dtype = 17;
   DbAttachInfo dbai = null;
   static String jdkVersion = null;

   void iscCreateDatabase(String var1, String var2, int var3, int var4, IscDbHandle var5, IBStruct var6, interbase.interclient.IBException var7) throws interbase.interclient.IBException, java.sql.SQLException {
      IscDbHandle var8 = var5;
      if (var5 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else {
         synchronized(var5) {
            this.dbai = new DbAttachInfo(var2, var3, var1, var4);
            this.connect(var8, this.dbai);

            try {
               var8.out.writeInt(20);
               var8.out.writeInt(0);
               var8.out.writeString(this.dbai.getFileNameSlash());
               var8.out.writeTyped(1, var6);
               var8.out.flush();

               try {
                  Response var10 = this.receiveResponse(var8, var7);
                  var8.setRdb_id(var10.resp_object);
                  this.dbai.setPartnerPort(var10.resp_blob_id);
               } catch (interbase.interclient.IBException var12) {
                  this.disconnect(var8);
                  throw var12;
               }
            } catch (IOException var13) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var13));
            }

         }
      }
   }

   void iscAttachDatabase(String var1, String var2, int var3, int var4, IscDbHandle var5, IBStruct var6, interbase.interclient.IBException var7) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var5 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else {
         synchronized(var5) {
            this.dbai = new DbAttachInfo(var2, var3, var1, var4);
            this.connect(var5, this.dbai);

            try {
               var5.out.writeInt(19);
               var5.out.writeInt(0);
               var5.out.writeString(this.dbai.getFileNameSlash());
               var5.out.writeTyped(1, var6);
               var5.out.flush();

               try {
                  Response var9 = this.receiveResponse(var5, var7);
                  var5.setRdb_id(var9.resp_object);
                  this.dbai.setPartnerPort(var9.resp_blob_id);
               } catch (interbase.interclient.IBException var11) {
                  this.disconnect(var5);
                  throw var11;
               }
            } catch (IOException var12) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var12));
            }

         }
      }
   }

   byte[] iscDatabaseInfo(IscDbHandle var1, byte[] var2, int var3, interbase.interclient.IBException var4) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else {
         synchronized(var1) {
            byte[] var10000;
            try {
               var1.out.writeInt(40);
               var1.out.writeInt(var1.getRdb_id());
               var1.out.writeInt(0);
               var1.out.writeBuffer(var2, var2.length);
               var1.out.writeInt(var3);
               var1.out.flush();
               Response var6 = this.receiveResponse(var1, var4);
               var10000 = var6.resp_data;
            } catch (IOException var8) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var8));
            }

            return var10000;
         }
      }
   }

   void iscDetachDatabase(IscDbHandle var1, interbase.interclient.IBException var2) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else {
         synchronized(var1) {
            try {
               var1.out.writeInt(21);
               var1.out.writeInt(var1.getRdb_id());
               var1.out.flush();
               this.receiveResponse(var1, var2);
            } catch (IOException var6) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var6));
            }

         }
      }
   }

   void iscDropDatabase(IscDbHandle var1, interbase.interclient.IBException var2) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else {
         synchronized(var1) {
            try {
               var1.out.writeInt(81);
               var1.out.writeInt(var1.getRdb_id());
               var1.out.flush();
               this.receiveResponse(var1, var2);
            } catch (IOException var6) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var6));
            }

         }
      }
   }

   byte[] iscExpandDpb(byte[] var1, int var2, int var3, Object[] var4) throws interbase.interclient.IBException {
      return var1;
   }

   void iscStartTransaction(interbase.interclient.IscTrHandle var1, IscDbHandle var2, Set var3, interbase.interclient.IBException var4) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544332);
      } else if (var2 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else {
         synchronized(var2) {
            if (var1.getState() != 0) {
               throw new interbase.interclient.IBException(335544468);
            } else {
               var1.setState(1);

               try {
                  var2.out.writeInt(29);
                  var2.out.writeInt(var2.getRdb_id());
                  var2.out.writeSet(3, var3);
                  var2.out.flush();
                  Response var6 = this.receiveResponse(var2, var4);
                  var1.rtr_id = var6.resp_object;
               } catch (IOException var8) {
                  throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var8));
               }

               var1.rtr_rdb = var2;
               var1.setState(2);
            }
         }
      }
   }

   void iscCommitTransaction(interbase.interclient.IscTrHandle var1, interbase.interclient.IBException var2) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544332);
      } else {
         IscDbHandle var3 = var1.rtr_rdb;
         synchronized(var3) {
            var1.setState(5);

            try {
               var3.out.writeInt(30);
               var3.out.writeInt(var1.rtr_id);
               var3.out.flush();
               this.receiveResponse(var3, var2);
            } catch (IOException var7) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var7));
            }

            var1.setState(0);
            var1.rtr_rdb = null;
         }
      }
   }

   void iscCommitRetaining(interbase.interclient.IscTrHandle var1, interbase.interclient.IBException var2) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544332);
      } else {
         IscDbHandle var3 = var1.rtr_rdb;
         synchronized(var3) {
            var1.setState(5);

            try {
               var3.out.writeInt(50);
               var3.out.writeInt(var1.rtr_id);
               var3.out.flush();
               this.receiveResponse(var3, var2);
            } catch (IOException var7) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var7));
            }

            var1.setState(2);
         }
      }
   }

   void iscPrepareTransaction(interbase.interclient.IscTrHandle var1, interbase.interclient.IBException var2) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544332);
      } else {
         IscDbHandle var3 = var1.rtr_rdb;
         synchronized(var3) {
            if (var1.getState() != 2) {
               throw new interbase.interclient.IBException(335544468);
            } else {
               var1.setState(3);

               try {
                  var3.out.writeInt(32);
                  var3.out.writeInt(var1.rtr_id);
                  var3.out.flush();
                  this.receiveResponse(var3, var2);
               } catch (IOException var7) {
                  throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var7));
               }

               var1.setState(4);
            }
         }
      }
   }

   void iscPrepareTransaction2(interbase.interclient.IscTrHandle var1, byte[] var2, interbase.interclient.IBException var3) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544332);
      } else {
         IscDbHandle var4 = var1.rtr_rdb;
         synchronized(var4) {
            if (var1.getState() != 2) {
               throw new interbase.interclient.IBException(335544468);
            } else {
               var1.setState(3);

               try {
                  var4.out.writeInt(51);
                  var4.out.writeInt(var1.rtr_id);
                  var4.out.writeBuffer(var2, var2.length);
                  var4.out.flush();
                  this.receiveResponse(var4, var3);
               } catch (IOException var8) {
                  throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var8));
               }

               var1.setState(4);
            }
         }
      }
   }

   void iscRollbackTransaction(interbase.interclient.IscTrHandle var1, interbase.interclient.IBException var2) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544332);
      } else {
         IscDbHandle var3 = var1.rtr_rdb;
         synchronized(var3) {
            if (var1.getState() != 2 && var1.getState() != 4) {
               throw new interbase.interclient.IBException(335544468);
            } else {
               var1.setState(6);

               try {
                  var3.out.writeInt(31);
                  var3.out.writeInt(var1.rtr_id);
                  var3.out.flush();
                  this.receiveResponse(var3, var2);
               } catch (IOException var7) {
                  throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var7));
               }

               var1.setState(0);
            }
         }
      }
   }

   void iscRollbackRetaining(interbase.interclient.IscTrHandle var1, interbase.interclient.IBException var2) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544332);
      } else {
         IscDbHandle var3 = var1.rtr_rdb;
         synchronized(var3) {
            if (var1.getState() != 2 && var1.getState() != 4) {
               throw new interbase.interclient.IBException(335544468);
            } else {
               var1.setState(6);

               try {
                  var3.out.writeInt(86);
                  var3.out.writeInt(var1.rtr_id);
                  var3.out.flush();
                  this.receiveResponse(var3, var2);
               } catch (IOException var7) {
                  throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var7));
               }

               var1.setState(2);
               var1.rtr_rdb = null;
            }
         }
      }
   }

   void iscDsqlAllocateStatement(IscDbHandle var1, IscStmtHandle var2, interbase.interclient.IBException var3) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else if (var2 == null) {
         throw new interbase.interclient.IBException(335544327);
      } else {
         synchronized(var1) {
            try {
               var1.out.writeInt(62);
               var1.out.writeInt(var1.getRdb_id());
               var1.out.flush();
               Response var5 = this.receiveResponse(var1, var3);
               var2.rsr_id = var5.resp_object;
            } catch (IOException var7) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var7));
            }

            var2.iscDbHandle = var1;
            var2.allRowsFetched = false;
         }
      }
   }

   void iscDsqlAllocStatement2(IscDbHandle var1, IscStmtHandle var2) throws interbase.interclient.IBException, java.sql.SQLException {
      throw new interbase.interclient.IBException(335544378);
   }

   interbase.interclient.XSQLDA iscDsqlDescribe(IscStmtHandle var1, int var2, interbase.interclient.IBException var3) throws interbase.interclient.IBException, java.sql.SQLException {
      byte[] var4;
      if (var1.getMinorDatabaseVersion() < 0) {
         var4 = new byte[]{4, 7, 9, 11, 12, 13, 14, 16, 17, 18, 19, 8};
      } else {
         var4 = new byte[]{4, 7, 9, 11, 12, 13, 14, 16, 17, 18, 19, 25, 8};
      }

      byte[] var5 = this.iscDsqlSqlInfo(var1, var4.length, var4, 1024, var3);
      return this.parseSqlInfo(var1, var5, var4, var3);
   }

   void iscDsqlDescribeBind(IscStmtHandle var1, interbase.interclient.IBException var2) throws interbase.interclient.IBException, java.sql.SQLException {
      byte[] var3;
      if (var1.getMinorDatabaseVersion() < 0) {
         var3 = new byte[]{5, 7, 9, 11, 12, 13, 14, 16, 17, 18, 19, 8};
      } else {
         var3 = new byte[]{5, 7, 9, 11, 12, 13, 14, 16, 17, 18, 19, 25, 8};
      }

      byte[] var4 = this.iscDsqlSqlInfo(var1, var3.length, var3, 1024, var2);
      var1.in_sqlda = this.parseSqlInfo(var1, var4, var3, var2);
   }

   void iscDsqlExecute(interbase.interclient.IscTrHandle var1, IscStmtHandle var2, interbase.interclient.XSQLDA var3, interbase.interclient.IBException var4) throws interbase.interclient.IBException, java.sql.SQLException {
      this.iscDsqlExecute2(var1, var2, var3, (interbase.interclient.XSQLDA)null, var4);
   }

   void iscDsqlExecute2(interbase.interclient.IscTrHandle var1, IscStmtHandle var2, interbase.interclient.XSQLDA var3, interbase.interclient.XSQLDA var4, interbase.interclient.IBException var5) throws interbase.interclient.IBException, java.sql.SQLException {
      IscDbHandle var6 = var2.iscDbHandle;
      var2.clearRows();
      synchronized(var6) {
         try {
            if (!this.isSQLDataOK(var3)) {
               throw new interbase.interclient.IBException(335544713);
            }

            var6.out.writeInt(var4 == null ? 63 : 76);
            var6.out.writeInt(var2.rsr_id);
            var6.out.writeInt(var1.rtr_id);
            this.writeBLR(var6, var3);
            var6.out.writeInt(0);
            var6.out.writeInt(var3 == null ? 0 : 1);
            if (var3 != null) {
               this.writeSQLData(var6, var3);
            }

            if (var4 != null) {
               this.writeBLR(var6, var4);
               var6.out.writeInt(0);
            }

            var6.out.flush();
            if (this.nextOperation(var6) == 78) {
               var2.rows.add(this.receiveSqlResponse(var6, var4));
               var2.allRowsFetched = true;
               var2.singletonResult = true;
            } else {
               var2.singletonResult = false;
            }

            this.receiveResponse(var6, var5);
         } catch (IOException var10) {
            throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var10));
         }

      }
   }

   void iscDsqlExecuteImmediate(IscDbHandle var1, interbase.interclient.IscTrHandle var2, String var3, int var4, interbase.interclient.XSQLDA var5, interbase.interclient.IBException var6) throws interbase.interclient.IBException, java.sql.SQLException {
      this.iscDsqlExecImmed2(var1, var2, var3, var4, var5, (interbase.interclient.XSQLDA)null, var6);
   }

   void iscDsqlExecImmed2(IscDbHandle var1, interbase.interclient.IscTrHandle var2, String var3, int var4, interbase.interclient.XSQLDA var5, interbase.interclient.XSQLDA var6, interbase.interclient.IBException var7) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else if (var2 == null) {
         throw new interbase.interclient.IBException(335544332);
      } else {
         synchronized(var1) {
            try {
               if (!this.isSQLDataOK(var5)) {
                  throw new interbase.interclient.IBException(335544713);
               }

               if (var5 == null && var6 == null) {
                  var1.out.writeInt(64);
               } else {
                  var1.out.writeInt(75);
                  this.writeBLR(var1, var5);
                  var1.out.writeInt(0);
                  var1.out.writeInt(var5 == null ? 0 : 1);
                  if (var5 != null) {
                     this.writeSQLData(var1, var5);
                  }

                  this.writeBLR(var1, var6);
                  var1.out.writeInt(0);
               }

               var1.out.writeInt(var2.rtr_id);
               var1.out.writeInt(0);
               var1.out.writeInt(var4);
               var1.out.writeString(var3, var1.charSetToUse_);
               var1.out.writeString("");
               var1.out.writeInt(0);
               var1.out.flush();
               if (this.nextOperation(var1) == 78) {
                  this.receiveSqlResponse(var1, var6);
               }

               this.receiveResponse(var1, var7);
            } catch (IOException var11) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var11));
            }

         }
      }
   }

   boolean iscDsqlFetch(IscStmtHandle var1, int var2, interbase.interclient.XSQLDA var3, interbase.interclient.IBException var4, int var5) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544327);
      } else if (var3 == null) {
         throw new interbase.interclient.IBException(335544583);
      } else {
         if (!var1.allRowsFetched) {
            IscDbHandle var6 = var1.iscDbHandle;
            synchronized(var6) {
               try {
                  var6.out.writeInt(65);
                  var6.out.writeInt(var1.rsr_id);
                  this.writeBLR(var6, var3);
                  var6.out.writeInt(0);
                  if (var5 <= 0) {
                     var6.out.writeInt(200);
                  } else {
                     var6.out.writeInt(var5);
                  }

                  var6.out.flush();
                  if (this.nextOperation(var6) != 66) {
                     this.receiveResponse(var6, var4);
                  } else {
                     int var8 = 0;
                     boolean var9 = false;

                     while(true) {
                        int var10 = this.readOperation(var6);
                        boolean var11;
                        if (var10 == 66) {
                           var8 = var6.in.readInt();
                           int var15 = var6.in.readInt();
                           if (var15 > 0 && var8 == 0) {
                              var1.rows.add(this.readSQLData(var6, var3));
                              var11 = true;
                           } else {
                              var11 = false;
                           }
                        } else {
                           var11 = false;
                        }

                        if (!var11) {
                           if (var10 == 9) {
                              this.receiveResponseAfterOp(var6, var4);
                           }

                           if (var8 == 100) {
                              var1.allRowsFetched = true;
                           }
                           break;
                        }
                     }
                  }
               } catch (IOException var13) {
                  throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var13));
               }
            }
         }

         return var1.rows.size() > 0;
      }
   }

   private IscDbHandle makePartner(IscDbHandle var1, String var2, int var3) throws IOException, java.sql.SQLException, interbase.interclient.IBException {
      IscDbHandle var4 = new IscDbHandle();
      this.connect_partner(var4, this.dbai);
      var4.out = new interbase.interclient.XdrOutputStream(new BufferedOutputStream(var4.socket.getOutputStream()));
      var4.in = new XdrInputStream(new BufferedInputStream(var4.socket.getInputStream()));
      var4.out.writeInt(54);
      var4.out.writeInt(2);
      var4.out.writeInt(0);
      var4.out.writeInt((int)this.dbai.getPartnerPort());
      var4.out.flush();
      return var4;
   }

   void iscDsqlFreeStatement(IscStmtHandle var1, int var2, interbase.interclient.IBException var3) throws interbase.interclient.IBException, java.sql.SQLException {
      IscDbHandle var4 = var1.iscDbHandle;
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544327);
      } else if (!var1.singletonResult || var2 != 1) {
         if (var2 == 4) {
            if (this.dbai != null) {
               try {
                  IscDbHandle var5 = this.makePartner(var4, this.dbai.getServer(), this.dbai.getPort());
                  var5.out.writeInt(67);
                  var5.out.writeInt(var1.rsr_id);
                  var5.out.writeInt(var2);
                  var5.out.flush();
                  this.receiveResponse(var5, var3);
                  var5.out.close();
                  var5.socket.close();
               } catch (IOException var8) {
                  throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var8));
               }
            }

         } else {
            synchronized(var4) {
               try {
                  var4.out.writeInt(67);
                  var4.out.writeInt(var1.rsr_id);
                  var4.out.writeInt(var2);
                  var4.out.flush();
                  this.receiveResponse(var4, var3);
                  if (var2 == 2) {
                     var1.in_sqlda = null;
                     var1.out_sqlda = null;
                  }

                  var1.clearRows();
               } catch (IOException var9) {
                  throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var9));
               }

            }
         }
      }
   }

   interbase.interclient.XSQLDA iscDsqlPrepare(interbase.interclient.IscTrHandle var1, IscStmtHandle var2, String var3, int var4, interbase.interclient.IBException var5) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544332);
      } else if (var2 == null) {
         throw new interbase.interclient.IBException(335544327);
      } else {
         IscDbHandle var6 = var2.iscDbHandle;
         var2.in_sqlda = null;
         var2.out_sqlda = null;
         byte[] var7;
         if (var2.getMinorDatabaseVersion() < 0) {
            var7 = new byte[]{4, 7, 9, 11, 12, 13, 14, 16, 17, 18, 19, 8};
         } else {
            var7 = new byte[]{4, 7, 9, 11, 12, 13, 14, 16, 17, 18, 19, 25, 8};
         }

         synchronized(var6) {
            interbase.interclient.XSQLDA var10000;
            try {
               var6.out.writeInt(68);
               var6.out.writeInt(var1.rtr_id);
               var6.out.writeInt(var2.rsr_id);
               var6.out.writeInt(var4);
               var6.out.writeString(var3, var6.charSetToUse_);
               var6.out.writeBuffer(var7, var7.length);
               var6.out.writeInt(1024);
               var6.out.flush();
               Response var9 = this.receiveResponse(var6, var5);
               var2.out_sqlda = this.parseSqlInfo(var2, var9.resp_data, var7, var5);
               var2.setStmtType(var9.resp_object);
               var10000 = var2.out_sqlda;
            } catch (IOException var11) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var11));
            }

            return var10000;
         }
      }
   }

   void iscDsqlSetCursorName(IscStmtHandle var1, String var2, int var3, interbase.interclient.IBException var4) throws interbase.interclient.IBException, java.sql.SQLException {
      IscDbHandle var5 = var1.iscDbHandle;
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544327);
      } else {
         synchronized(var5) {
            try {
               var5.out.writeInt(69);
               var5.out.writeInt(var1.rsr_id);
               byte[] var7 = new byte[var2.length() + 1];
               System.arraycopy(var2.getBytes(), 0, var7, 0, var2.length());
               var7[var2.length()] = 0;
               var5.out.writeBuffer(var7, var7.length);
               var5.out.writeInt(0);
               var5.out.flush();
               this.receiveResponse(var5, var4);
            } catch (IOException var9) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var9));
            }

         }
      }
   }

   byte[] iscDsqlSqlInfo(IscStmtHandle var1, int var2, byte[] var3, int var4, interbase.interclient.IBException var5) throws java.sql.SQLException {
      IscDbHandle var6 = var1.iscDbHandle;
      synchronized(var6) {
         byte[] var10000;
         try {
            var6.out.writeInt(70);
            var6.out.writeInt(var1.rsr_id);
            var6.out.writeInt(0);
            var6.out.writeBuffer(var3, var2);
            var6.out.writeInt(var4);
            var6.out.flush();
            Response var8 = this.receiveResponse(var6, var5);
            var10000 = var8.resp_data;
         } catch (IOException var10) {
            throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var10));
         }

         return var10000;
      }
   }

   static int iscVaxInteger(byte[] var0, int var1, int var2) {
      int var4 = 0;
      int var3 = 0;
      int var5 = var1;

      while(true) {
         --var2;
         if (var2 < 0) {
            return var3;
         }

         var3 += (var0[var5++] & 255) << var4;
         var4 += 8;
      }
   }

   static int iscReadInt(byte[] var0, int var1, int var2) {
      int var3 = 0;
      int var4 = 0;
      int var5 = var1 + var2 - 1;

      while(true) {
         --var2;
         if (var2 < 0) {
            return var3;
         }

         var3 += (var0[var5--] & 255) << var4;
         var4 += 8;
      }
   }

   void iscCreateBlob2(IscDbHandle var1, interbase.interclient.IscTrHandle var2, interbase.interclient.IscBlobHandle var3, IBStruct var4, interbase.interclient.IBException var5) throws interbase.interclient.IBException, java.sql.SQLException {
      this.openOrCreateBlob(var1, var2, var3, var4, var4 == null ? 34 : 57, var5);
      var3.rbl_flags |= 8;
   }

   void iscOpenBlob2(IscDbHandle var1, interbase.interclient.IscTrHandle var2, interbase.interclient.IscBlobHandle var3, IBStruct var4, interbase.interclient.IBException var5) throws interbase.interclient.IBException, java.sql.SQLException {
      this.openOrCreateBlob(var1, var2, var3, var4, var4 == null ? 35 : 56, var5);
   }

   private final void openOrCreateBlob(IscDbHandle var1, interbase.interclient.IscTrHandle var2, interbase.interclient.IscBlobHandle var3, IBStruct var4, int var5, interbase.interclient.IBException var6) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var1 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else if (var2 == null) {
         throw new interbase.interclient.IBException(335544332);
      } else if (var3 == null) {
         throw new interbase.interclient.IBException(335544328);
      } else {
         synchronized(var1) {
            try {
               var1.out.writeInt(var5);
               if (var4 != null) {
                  var1.out.writeTyped(1, var4);
               }

               var1.out.writeInt(var2.rtr_id);
               var1.out.writeLong(var3.blob_id);
               var1.out.flush();
               Response var8 = this.receiveResponse(var1, var6);
               var3.db = var1;
               var3.tr = var2;
               var3.rbl_id = var8.resp_object;
               var3.blob_id = var8.resp_blob_id;
            } catch (IOException var10) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var10));
            }

         }
      }
   }

   byte[] iscGetSegment(interbase.interclient.IscBlobHandle var1, int var2, interbase.interclient.IBException var3) throws interbase.interclient.IBException, java.sql.SQLException {
      IscDbHandle var4 = var1.db;
      if (var4 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else {
         synchronized(var4) {
            byte[] var10000;
            try {
               var4.out.writeInt(36);
               var4.out.writeInt(var1.rbl_id);
               var4.out.writeInt(Math.min(var2 + 2, 32767));
               var4.out.writeInt(0);
               var4.out.flush();
               Response var6 = this.receiveResponse(var4, var3);
               var1.rbl_flags &= -3;
               if (var6.resp_object == 1) {
                  var1.rbl_flags |= 2;
               } else if (var6.resp_object == 2) {
                  var1.rbl_flags |= 4;
               }

               byte[] var7 = var6.resp_data;
               if (var7.length != 0) {
                  boolean var8 = false;
                  int var9 = 0;

                  int var10;
                  int var15;
                  for(var10 = 0; var9 < var7.length; var10 += var15) {
                     var15 = iscVaxInteger(var7, var9, 2);
                     var9 += 2;
                     System.arraycopy(var7, var9, var7, var10, var15);
                     var9 += var15;
                  }

                  byte[] var11 = new byte[var10];
                  System.arraycopy(var7, 0, var11, 0, var10);
                  var10000 = var11;
                  return var10000;
               }

               var10000 = var7;
            } catch (IOException var13) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var13));
            }

            return var10000;
         }
      }
   }

   void iscPutSegment(interbase.interclient.IscBlobHandle var1, byte[] var2, interbase.interclient.IBException var3) throws interbase.interclient.IBException, java.sql.SQLException {
      interbase.interclient.IscBlobHandle var4 = var1;
      IscDbHandle var5 = var1.db;
      if (var5 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else {
         synchronized(var5) {
            try {
               var5.out.writeInt(44);
               var5.out.writeInt(var4.rbl_id);
               var5.out.writeBlobBuffer(var2);
               var5.out.flush();
               this.receiveResponse(var5, var3);
            } catch (IOException var9) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var9));
            }

         }
      }
   }

   void iscCloseBlob(interbase.interclient.IscBlobHandle var1, interbase.interclient.IBException var2) throws interbase.interclient.IBException, java.sql.SQLException {
      IscDbHandle var3 = var1.db;
      if (var3 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else {
         interbase.interclient.IscTrHandle var4 = var1.tr;
         if (var4 == null) {
            throw new interbase.interclient.IBException(335544332);
         } else {
            this.releaseObject(var3, 39, var1.rbl_id, var2);
         }
      }
   }

   Object IscGetSlice(int[] var1, interbase.interclient.ArrayDescriptor var2, IscDbHandle var3, interbase.interclient.IscTrHandle var4) throws interbase.interclient.IBException, java.sql.SQLException {
      Object var5 = null;
      if (var3 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else if (var4 == null) {
         throw new interbase.interclient.IBException(335544332);
      } else if (var2 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else {
         synchronized(var3) {
            try {
               var3.out.writeInt(36);
            } catch (IOException var9) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var9));
            }

            return var5;
         }
      }
   }

   void iscStartSavepoint(String var1, IscDbHandle var2, interbase.interclient.IscTrHandle var3, interbase.interclient.IBException var4) throws interbase.interclient.IBException, java.sql.SQLException {
      this.writeSavepoint(90, var1, var2, var3, var4);
   }

   void iscReleaseSavepoint(String var1, IscDbHandle var2, interbase.interclient.IscTrHandle var3, interbase.interclient.IBException var4) throws interbase.interclient.IBException, java.sql.SQLException {
      this.writeSavepoint(88, var1, var2, var3, var4);
   }

   void iscRollbackSavepoint(String var1, IscDbHandle var2, interbase.interclient.IscTrHandle var3, interbase.interclient.IBException var4) throws interbase.interclient.IBException, java.sql.SQLException {
      this.writeSavepoint(89, var1, var2, var3, var4);
   }

   void writeSavepoint(int var1, String var2, IscDbHandle var3, interbase.interclient.IscTrHandle var4, interbase.interclient.IBException var5) throws interbase.interclient.IBException, java.sql.SQLException {
      if (var3 == null) {
         throw new interbase.interclient.IBException(335544324);
      } else if (var4 == null) {
         throw new interbase.interclient.IBException(335544332);
      } else {
         synchronized(var3) {
            try {
               var3.out.writeInt(var1);
               var3.out.writeInt(var4.rtr_id);
               int var7 = var2.length();
               byte[] var8 = new byte[var7 + 1];
               System.arraycopy(var2.getBytes(), 0, var8, 0, var7);
               var8[var7] = 0;
               var3.out.writeBuffer(var8, var7);
               var3.out.writeInt(0);
               var3.out.flush();
               this.receiveResponse(var3, var5);
            } catch (IOException var10) {
               throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var10));
            }

         }
      }
   }

   IscDbHandle getNewIscDbHandle(String var1) {
      return new IscDbHandle(var1);
   }

   interbase.interclient.IscTrHandle getNewIscTrHandle() {
      return new interbase.interclient.IscTrHandle();
   }

   IscStmtHandle getNewIscStmtHandle(int var1, int var2) {
      return new IscStmtHandle(var1, var2);
   }

   interbase.interclient.IscBlobHandle getNewIscBlobHandle() {
      return new interbase.interclient.IscBlobHandle();
   }

   private void connect_partner(IscDbHandle var1, DbAttachInfo var2) throws java.sql.SQLException {
      try {
         var1.socket = new Socket(var2.getServer(), var2.getPort());
         var1.socket.setTcpNoDelay(true);
      } catch (IOException var4) {
         throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_connect_01__, var2.getServer(), Utils.getMessage(var4));
      }
   }

   private void connect(IscDbHandle var1, DbAttachInfo var2) throws interbase.interclient.IBException, java.sql.SQLException {
      try {
         boolean var3;
         do {
            this.sendIbConnect(var1, var2);
            int var4 = this.readOperation(var1);
            if (3 == var4) {
               var1.in.readInt();
               var1.in.readInt();
               var1.in.readInt();
               var3 = true;
            } else {
               if (91 != var4) {
                  this.disconnect(var1);
                  var3 = true;
                  throw new interbase.interclient.IBException(335544421);
               }

               var3 = false;
               var2.setPort(var1.in.readInt());
               this.disconnect(var1);
            }
         } while(!var3);

      } catch (IOException var5) {
         throw new interbase.interclient.IBException(335544721);
      }
   }

   private void sendIbConnect(IscDbHandle var1, DbAttachInfo var2) throws interbase.interclient.IBException, IOException {
      try {
         var1.socket = new Socket(var2.getServer(), var2.getPort());
         var1.socket.setTcpNoDelay(true);
         if (var2.timeOut_ != 0) {
            var1.socket.setSoTimeout(var2.timeOut_);
         }

         if (jdkVersion == null) {
            jdkVersion = System.getProperty("java.version");
         }

         if (!jdkVersion.startsWith("1.2") && !jdkVersion.startsWith("1.1")) {
            var1.socket.setKeepAlive(true);
         }
      } catch (IOException var7) {
         interbase.interclient.IBException var4 = new interbase.interclient.IBException(335544721);
         var4.setNext(new interbase.interclient.IBException(2, var2.getServer() + ":" + var2.getPort()));
         var4.setNextException(new java.sql.SQLException(var7.getMessage()));
         throw var4;
      }

      var1.out = new interbase.interclient.XdrOutputStream(new BufferedOutputStream(var1.socket.getOutputStream()));
      var1.in = new XdrInputStream(new BufferedInputStream(var1.socket.getInputStream()));
      String var3 = System.getProperty("user.name");
      String var8 = InetAddress.getLocalHost().getHostName();
      byte[] var5 = new byte[200];
      byte var6 = 0;
      int var9 = var6 + 1;
      var5[var6] = 1;
      var5[var9++] = (byte)var3.length();
      System.arraycopy(var3.getBytes(), 0, var5, var9, var3.length());
      var9 += var3.length();
      var5[var9++] = 4;
      var5[var9++] = (byte)var8.length();
      System.arraycopy(var8.getBytes(), 0, var5, var9, var8.length());
      var9 += var8.length();
      var5[var9++] = 6;
      var5[var9++] = 0;
      var1.out.writeInt(1);
      var1.out.writeInt(19);
      var1.out.writeInt(2);
      var1.out.writeInt(1);
      var1.out.writeString(var2.getFileNameSlash());
      var1.out.writeInt(1);
      var1.out.writeBuffer(var5, var9);
      var1.out.writeInt(12);
      var1.out.writeInt(1);
      var1.out.writeInt(2);
      var1.out.writeInt(3);
      var1.out.writeInt(2);
      var1.out.flush();
   }

   private void disconnect(IscDbHandle var1) throws IOException {
      if (var1.out != null) {
         var1.out.close();
         var1.out = null;
      }

      if (var1.in != null) {
         var1.in.close();
         var1.in = null;
      }

      var1.socket.close();
   }

   private Object[] receiveSqlResponse(IscDbHandle var1, interbase.interclient.XSQLDA var2) throws interbase.interclient.IBException, java.sql.SQLException {
      try {
         if (this.readOperation(var1) == 78) {
            int var3 = var1.in.readInt();
            return var3 > 0 ? this.readSQLData(var1, var2) : null;
         } else {
            throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__);
         }
      } catch (IOException var4) {
         throw new interbase.interclient.IBException(335544726, var4.toString());
      }
   }

   private Response receiveResponseAfterOp(IscDbHandle var1, interbase.interclient.IBException var2) throws interbase.interclient.IBException, java.sql.SQLException {
      try {
         Response var3 = new Response();
         var3.resp_object = var1.in.readInt();
         var3.resp_blob_id = var1.in.readLong();
         var3.resp_data = var1.in.readBuffer();
         this.readStatusVector(var1, var2);
         return var3;
      } catch (IOException var4) {
         throw new interbase.interclient.IBException(335544726, var4.toString());
      }
   }

   private Response receiveResponse(IscDbHandle var1, interbase.interclient.IBException var2) throws interbase.interclient.IBException, java.sql.SQLException {
      try {
         int var3 = this.readOperation(var1);
         return var3 == 9 ? this.receiveResponseAfterOp(var1, var2) : null;
      } catch (IOException var4) {
         throw new interbase.interclient.IBException(335544726, var4.toString());
      }
   }

   private int nextOperation(IscDbHandle var1) throws IOException {
      do {
         var1.op = var1.in.readInt();
         if (var1.op == 71) {
            ;
         }
      } while(var1.op == 71);

      return var1.op;
   }

   private int readOperation(IscDbHandle var1) throws IOException {
      int var2 = var1.op >= 0 ? var1.op : this.nextOperation(var1);
      var1.op = -1;
      return var2;
   }

   private void readStatusVector(IscDbHandle var1, interbase.interclient.IBException var2) throws interbase.interclient.IBException, java.sql.SQLException {
      try {
         interbase.interclient.IBException var3 = null;
         interbase.interclient.IBException var4 = null;
         var2 = null;
         boolean var5 = false;

         while(true) {
            int var6 = var1.in.readInt();
            switch(var6) {
            case 0:
               if (var3 != null && var5) {
                  throw var3;
               } else {
                  return;
               }
            case 2:
            case 5:
               interbase.interclient.IBException var7 = new interbase.interclient.IBException(var6, var1.in.readString());
               if (var3 == null) {
                  var3 = var7;
                  var4 = var7;
               } else {
                  var4.setNext(var7);
                  var4 = var7;
               }
               break;
            case 4:
            case 18:
            default:
               int var8 = var1.in.readInt();
               if (var8 != 0) {
                  if (var6 == 1) {
                     var5 = true;
                  }

                  interbase.interclient.IBException var9 = new interbase.interclient.IBException(var6, var8);
                  if (var3 == null) {
                     var3 = var9;
                     var4 = var9;
                  } else {
                     var4.setNext(var9);
                     var4 = var9;
                  }
               }
            }
         }
      } catch (IOException var10) {
         throw new interbase.interclient.IBException(335544721, var10.toString());
      }
   }

   private void writeBLR(IscDbHandle var1, interbase.interclient.XSQLDA var2) throws interbase.interclient.IBException, java.sql.SQLException {
      int var3 = 0;
      byte[] var4 = null;
      if (var2 != null) {
         var3 = 8;
         int var5 = 0;

         int var7;
         for(int var6 = 0; var6 < var2.sqld; ++var6) {
            var7 = var2.sqlvar[var6].sqltype & -2;
            if (var7 != 448 && var7 != 452) {
               if (var7 != 500 && var7 != 496 && var7 != 580 && var7 != 550 && var7 != 520 && var7 != 540 && var7 != 590) {
                  ++var3;
               } else {
                  var3 += 2;
               }
            } else {
               var3 += 3;
            }

            var3 += 2;
            var5 += 2;
         }

         var4 = new byte[var3];
         byte var12 = 0;
         var7 = var12 + 1;
         var4[var12] = 5;
         var4[var7++] = 2;
         var4[var7++] = 4;
         var4[var7++] = 0;
         var4[var7++] = (byte)(var5 & 255);
         var4[var7++] = (byte)(var5 >> 8);

         for(int var8 = 0; var8 < var2.sqld; ++var8) {
            int var9 = var2.sqlvar[var8].sqltype & -2;
            int var10 = var2.sqlvar[var8].sqllen;
            if (var9 == 448) {
               var4[var7++] = 37;
               var4[var7++] = (byte)(var10 & 255);
               var4[var7++] = (byte)(var10 >> 8);
            } else if (var9 == 452) {
               var4[var7++] = 14;
               var4[var7++] = (byte)(var10 & 255);
               var4[var7++] = (byte)(var10 >> 8);
            } else if (var9 == 480) {
               var4[var7++] = 27;
            } else if (var9 == 482) {
               var4[var7++] = 10;
            } else if (var9 == 530) {
               var4[var7++] = 11;
            } else if (var9 == 570) {
               var4[var7++] = 12;
            } else if (var9 == 560) {
               var4[var7++] = 13;
            } else if (var9 == 510) {
               var4[var7++] = 35;
            } else if (var9 == 520) {
               var4[var7++] = 9;
               var4[var7++] = 0;
            } else if (var9 == 540) {
               var4[var7++] = 9;
               var4[var7++] = 0;
            } else if (var9 == 496) {
               var4[var7++] = 8;
               var4[var7++] = (byte)var2.sqlvar[var8].sqlscale;
            } else if (var9 == 500) {
               var4[var7++] = 7;
               var4[var7++] = (byte)var2.sqlvar[var8].sqlscale;
            } else if (var9 == 590) {
               var4[var7++] = 17;
               var4[var7++] = (byte)var2.sqlvar[var8].sqlscale;
            } else if (var9 == 580) {
               var4[var7++] = 16;
               var4[var7++] = (byte)var2.sqlvar[var8].sqlscale;
            } else if (var9 == 550) {
               var4[var7++] = 9;
               var4[var7++] = (byte)var2.sqlvar[var8].sqlscale;
            }

            var4[var7++] = 7;
            var4[var7++] = 0;
         }

         var4[var7++] = -1;
         var4[var7++] = 76;
      }

      try {
         var1.out.writeBuffer(var4, var3);
      } catch (IOException var11) {
         throw new interbase.interclient.IBException(335544727);
      }
   }

   private boolean isSQLDataOK(interbase.interclient.XSQLDA var1) {
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.sqld; ++var2) {
            if (var1.sqlvar[var2].sqlind != -1 && var1.sqlvar[var2].sqldata == null) {
               return false;
            }
         }
      }

      return true;
   }

   private void writeSQLData(IscDbHandle var1, interbase.interclient.XSQLDA var2) throws interbase.interclient.IBException, java.sql.SQLException {
      for(int var3 = 0; var3 < var2.sqld; ++var3) {
         this.writeSQLDatum(var1, var2.sqlvar[var3]);
      }

   }

   private void writeSQLDatum(IscDbHandle var1, XSQLVAR var2) throws interbase.interclient.IBException, java.sql.SQLException {
      this.fixNull(var2);

      try {
         Object var4 = var2.sqldata;
         byte[] var3;
         IBTimestamp var5;
         String var10;
         switch(var2.sqltype & -2) {
         case 448:
            var10 = this.getCharSetToUse(var1.charSetToUse_, var2);
            byte[] var6 = ((String)var4).getBytes(var10);
            int var7 = Math.min(var6.length, var2.sqllen);
            var1.out.writeInt(var7);
            if (var7 < var6.length) {
               var3 = new byte[var7];

               for(int var8 = 0; var8 < var7; ++var8) {
                  var3[var8] = var6[var8];
               }
            } else {
               var3 = var6;
            }

            var1.out.writeOpaque(var3, var3.length);
            break;
         case 452:
            var10 = this.getCharSetToUse(var1.charSetToUse_, var2);
            if (var4 instanceof String) {
               var3 = this.fillString((String)var4, var2.sqllen, var10);
            } else {
               var3 = this.fillString(var4.toString(), var2.sqllen, var10);
            }

            var1.out.writeOpaque(var3, var3.length);
            break;
         case 480:
            var1.out.writeDouble(((Double)var4).doubleValue());
            break;
         case 482:
            var1.out.writeFloat(((Float)var4).floatValue());
            break;
         case 496:
            var1.out.writeInt(((Integer)var4).intValue());
            break;
         case 500:
            var1.out.writeInt(((Short)var4).shortValue());
            break;
         case 510:
            new IBTimestamp();
            if (var4 instanceof IBTimestamp) {
               var1.out.writeInt(((IBTimestamp)var4).getEncodedYearMonthDay());
               var1.out.writeInt(((IBTimestamp)var4).getEncodedHourMinuteSecond());
            } else {
               var1.out.writeInt(this.encodeDate((Timestamp)var4));
               var1.out.writeInt(this.encodeTime((Timestamp)var4));
            }
            break;
         case 520:
            var1.out.writeLong(((Long)var4).longValue());
            break;
         case 540:
            var1.out.writeLong(((Long)var4).longValue());
            break;
         case 550:
            var1.out.writeLong(((Long)var4).longValue());
            break;
         case 560:
            var5 = new IBTimestamp();
            if (var4.getClass().isInstance(var5)) {
               var1.out.writeInt(((IBTimestamp)var4).getEncodedHourMinuteSecond());
            } else {
               var1.out.writeInt(this.encodeTime((Time)var4));
            }
            break;
         case 570:
            var5 = new IBTimestamp();
            if (var4.getClass().isInstance(var5)) {
               var1.out.writeInt(((IBTimestamp)var4).getEncodedYearMonthDay());
            } else {
               var1.out.writeInt(this.encodeDate((Date)var4));
            }
            break;
         case 580:
            var1.out.writeLong(((Long)var4).longValue());
            break;
         case 590:
            var1.out.writeInt(((Short)var4).shortValue());
            break;
         default:
            System.out.println("Unknown sql data type: " + var2.sqltype);
         }

         var1.out.writeInt(var2.sqlind);
      } catch (IOException var9) {
         throw new interbase.interclient.IBException(335544727);
      }
   }

   private String getCharSetToUse(String var1, XSQLVAR var2) {
      if (var1 != null) {
         return var1;
      } else {
         String var3 = CharacterEncodings.getCharacterSetName(var2.sqlsubtype % 128);
         return var3 != null ? var3 : "8859_1";
      }
   }

   private void fixNull(XSQLVAR var1) {
      if (var1.sqlind == -1 && var1.sqldata == null) {
         switch(var1.sqltype & -2) {
         case 448:
            byte[] var2 = new byte[0];
            var1.sqldata = new String(var2);
            break;
         case 452:
            var1.sqldata = new byte[var1.sqllen];
            break;
         case 480:
            var1.sqldata = new Double(0.0D);
            break;
         case 482:
            var1.sqldata = new Float(0.0F);
            break;
         case 496:
            var1.sqldata = new Integer(0);
            break;
         case 500:
         case 590:
            var1.sqldata = new Short((short)0);
            break;
         case 510:
            var1.sqldata = new Timestamp(0L);
            break;
         case 520:
         case 540:
         case 550:
         case 580:
            var1.sqldata = new Long(0L);
            break;
         case 560:
            var1.sqldata = new Time(0L);
            break;
         case 570:
            var1.sqldata = new Date(0L);
            break;
         default:
            System.out.println("Unknown sql data type: " + var1.sqltype);
         }
      }

   }

   private byte[] fillString(String var1, int var2, String var3) throws UnsupportedEncodingException {
      byte[] var4 = var1.getBytes(var3);
      int var6;
      if (var4.length < var2) {
         StringBuffer var5 = new StringBuffer();
         var5.ensureCapacity(var2);
         var5.append(var1);

         for(var6 = 0; var6 < var2 - var4.length; ++var6) {
            var5.append(' ');
         }

         var4 = var5.toString().getBytes(var3);
      } else if (var4.length > var2) {
         byte[] var7 = new byte[var2];

         for(var6 = 0; var6 < var2; ++var6) {
            var7[var6] = var4[var6];
         }

         return var7;
      }

      return var4;
   }

   private int encodeTime(java.util.Date var1) {
      IBTimestamp var2 = new IBTimestamp(var1);
      return var2.getEncodedHourMinuteSecond();
   }

   private int encodeDate(java.util.Date var1) {
      IBTimestamp var2 = new IBTimestamp(var1);
      return var2.getEncodedYearMonthDay();
   }

   private Object[] readSQLData(IscDbHandle var1, interbase.interclient.XSQLDA var2) throws interbase.interclient.IBException, java.sql.SQLException {
      Object[] var3 = new Object[var2.sqld];

      for(int var4 = 0; var4 < var2.sqld; ++var4) {
         var3[var4] = this.readSQLDatum(var1, var2.sqlvar[var4]);
      }

      return var3;
   }

   private Object readSQLDatum(IscDbHandle var1, XSQLVAR var2) throws interbase.interclient.IBException, java.sql.SQLException {
      try {
         switch(var2.sqltype & -2) {
         case 448:
            var2.sqldata = var1.in.readOpaque(var1.in.readInt());
            break;
         case 452:
            var2.sqldata = var1.in.readOpaque(var2.sqllen);
            break;
         case 480:
            var2.sqldata = new Double(var1.in.readDouble());
            break;
         case 482:
            var2.sqldata = new Float(var1.in.readFloat());
            break;
         case 496:
            var2.sqldata = new Integer(var1.in.readInt());
            break;
         case 500:
         case 590:
            var2.sqldata = new Short((short)var1.in.readInt());
            break;
         case 510:
            int[] var3 = new int[]{var1.in.readInt(), var1.in.readInt()};
            IBTimestamp var4 = new IBTimestamp(2, var3);
            var2.sqldata = new Timestamp(var4.getTimeInMillis());
            ((Timestamp)var2.sqldata).setNanos(var4.getNanos());
            break;
         case 520:
            var2.sqldata = new Long(var1.in.readLong());
            break;
         case 540:
            var2.sqldata = new Long(var1.in.readLong());
            break;
         case 550:
            var2.sqldata = new Long(var1.in.readLong());
            break;
         case 560:
            var2.sqldata = this.decodeTime(var1.in.readInt());
            break;
         case 570:
            var2.sqldata = this.decodeDate(var1.in.readInt());
            break;
         case 580:
            var2.sqldata = new Long(var1.in.readLong());
         }

         var2.sqlind = var1.in.readInt();
         if (var2.sqlind == 0) {
            return var2.sqldata;
         } else if (var2.sqlind == -1) {
            return null;
         } else {
            interbase.interclient.IBException var5 = new interbase.interclient.IBException(225544005);
            var5.setNext(new interbase.interclient.IBException(4, var2.sqlind));
            throw var5;
         }
      } catch (IOException var6) {
         throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var6));
      }
   }

   private Time decodeTime(int var1) {
      IBTimestamp var2 = null;
      var2 = new IBTimestamp(1, var1);
      return new Time(var2.getJustTime());
   }

   private Date decodeDate(int var1) {
      IBTimestamp var2 = null;
      var2 = new IBTimestamp(0, var1);
      return new Date(var2.getJustDate());
   }

   private interbase.interclient.XSQLDA parseSqlInfo(IscStmtHandle var1, byte[] var2, byte[] var3, interbase.interclient.IBException var4) throws interbase.interclient.IBException, java.sql.SQLException {
      interbase.interclient.XSQLDA var5 = new interbase.interclient.XSQLDA();

      byte[] var7;
      for(int var6 = 0; (var6 = this.parseTruncSqlInfo(var2, var5, var6)) > 0; var2 = this.iscDsqlSqlInfo(var1, var7.length, var7, var2.length, var4)) {
         --var6;
         var7 = new byte[4 + var3.length];
         var7[0] = 20;
         var7[1] = 2;
         var7[2] = (byte)(var6 & 255);
         var7[3] = (byte)(var6 >> 8);
         System.arraycopy(var3, 0, var7, 4, var3.length);
      }

      return var5;
   }

   private int parseTruncSqlInfo(byte[] var1, interbase.interclient.XSQLDA var2, int var3) throws interbase.interclient.IBException {
      int var5 = 0;
      byte var6 = 2;
      int var7 = iscVaxInteger(var1, var6, 2);
      int var9 = var6 + 2;
      int var8 = iscVaxInteger(var1, var9, var7);
      var9 += var7;
      if (var2.sqlvar == null) {
         var2.sqld = var2.sqln = var8;
         var2.sqlvar = new XSQLVAR[var2.sqln];
      }

      while(var1[var9] != 1) {
         byte var4;
         while((var4 = var1[var9++]) != 8) {
            switch(var4) {
            case 2:
               return var3;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 15:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            default:
               throw new interbase.interclient.IBException(335544583);
            case 9:
               var7 = iscVaxInteger(var1, var9, 2);
               var9 += 2;
               var5 = iscVaxInteger(var1, var9, var7);
               var9 += var7;
               var2.sqlvar[var5 - 1] = new XSQLVAR();
               break;
            case 11:
               var7 = iscVaxInteger(var1, var9, 2);
               var9 += 2;
               var2.sqlvar[var5 - 1].sqltype = iscVaxInteger(var1, var9, var7);
               var9 += var7;
               break;
            case 12:
               var7 = iscVaxInteger(var1, var9, 2);
               var9 += 2;
               var2.sqlvar[var5 - 1].sqlsubtype = iscVaxInteger(var1, var9, var7);
               var9 += var7;
               break;
            case 13:
               var7 = iscVaxInteger(var1, var9, 2);
               var9 += 2;
               var2.sqlvar[var5 - 1].sqlscale = iscVaxInteger(var1, var9, var7);
               var9 += var7;
               break;
            case 14:
               var7 = iscVaxInteger(var1, var9, 2);
               var9 += 2;
               var2.sqlvar[var5 - 1].sqllen = iscVaxInteger(var1, var9, var7);
               var9 += var7;
               break;
            case 16:
               var7 = iscVaxInteger(var1, var9, 2);
               var9 += 2;
               var2.sqlvar[var5 - 1].sqlname = new String(var1, var9, var7);
               var9 += var7;
               break;
            case 17:
               var7 = iscVaxInteger(var1, var9, 2);
               var9 += 2;
               var2.sqlvar[var5 - 1].relname = new String(var1, var9, var7);
               var9 += var7;
               break;
            case 18:
               var7 = iscVaxInteger(var1, var9, 2);
               var9 += 2;
               var2.sqlvar[var5 - 1].ownname = new String(var1, var9, var7);
               var9 += var7;
               break;
            case 19:
               var7 = iscVaxInteger(var1, var9, 2);
               var9 += 2;
               var2.sqlvar[var5 - 1].aliasname = new String(var1, var9, var7);
               var9 += var7;
               break;
            case 25:
               var7 = iscVaxInteger(var1, var9, 2);
               var9 += 2;
               var2.sqlvar[var5 - 1].sqlPrecision = iscVaxInteger(var1, var9, var7);
               var9 += var7;
            }
         }

         var3 = var5;
      }

      return 0;
   }

   private void releaseObject(IscDbHandle var1, int var2, int var3, interbase.interclient.IBException var4) throws interbase.interclient.IBException, java.sql.SQLException {
      synchronized(var1) {
         try {
            var1.out.writeInt(var2);
            var1.out.writeInt(var3);
            var1.out.flush();
            this.receiveResponse(var1, var4);
         } catch (IOException var8) {
            throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_read_0__, Utils.getMessage(var8));
         }

      }
   }

   static IBStruct newStructlet(int var0, String var1) {
      return new IBStruct(var0, var1.getBytes());
   }

   static IBStruct newStructlet(int var0) {
      return new IBStruct(var0, new byte[0]);
   }

   static IBStruct newStructlet(int var0, int var1) {
      return new IBStruct(var0, new byte[]{(byte)var1, (byte)(var1 >> 8), (byte)(var1 >> 16), (byte)(var1 >> 24)});
   }

   static IBStruct newStructlet(int var0, byte[] var1) {
      return new IBStruct(var0, var1);
   }

   static IBStruct cloneStructlet(IBStruct var0) {
      return var0 == null ? null : new IBStruct(var0);
   }

   class NamelessClass_1 {
   }

   private static class Response {
      int resp_object;
      long resp_blob_id;
      byte[] resp_data;

      private Response() {
      }

      // $FF: synthetic method
      Response(NamelessClass_1 var1) {
         this();
      }
   }

   static class DbAttachInfo {
      private String server;
      private int port = 3050;
      private String fileName;
      private String fileNameSlash;
      private int timeOut_;
      private long partnerPort = 0L;

      DbAttachInfo(String var1) throws java.sql.SQLException {
         if (var1 == null) {
            interbase.interclient.IBException var4 = new interbase.interclient.IBException(225544007);
            throw var4.getSQLExceptionFromIBE();
         } else {
            var1 = var1.trim();
            int var2 = var1.indexOf(58);
            interbase.interclient.IBException var3;
            if (var2 != 0 && var2 != var1.length() - 1) {
               if (var2 > 0) {
                  this.server = var1.substring(0, var2);
                  this.fileName = var1.substring(var2 + 1);
                  var2 = this.server.indexOf(47);
                  if (var2 == 0 || var2 == this.server.length() - 1) {
                     var3 = new interbase.interclient.IBException(335544323);
                     var3.setNext(new interbase.interclient.IBException(2, this.server));
                     throw var3.getSQLExceptionFromIBE();
                  }

                  if (var2 > 0) {
                     this.port = Integer.parseInt(this.server.substring(var2 + 1));
                     this.server = this.server.substring(0, var2);
                  }
               }

            } else {
               var3 = new interbase.interclient.IBException(335544323);
               var3.setNext(new interbase.interclient.IBException(2, var1));
               throw var3.getSQLExceptionFromIBE();
            }
         }
      }

      DbAttachInfo(String var1, int var2, String var3, int var4) {
         this.server = var1;
         this.port = var2;
         this.fileName = var3;
         this.fileNameSlash = var3.replace('\\', '/');
         this.timeOut_ = var4;
      }

      String getServer() {
         return this.server;
      }

      int getPort() {
         return this.port;
      }

      void setPort(int var1) {
         this.port = var1;
      }

      String getFileName() {
         return this.fileName;
      }

      String getFileNameSlash() {
         return this.fileNameSlash;
      }

      long getPartnerPort() {
         return this.partnerPort;
      }

      void setPartnerPort(long var1) {
         this.partnerPort = var1;
      }
   }
}
