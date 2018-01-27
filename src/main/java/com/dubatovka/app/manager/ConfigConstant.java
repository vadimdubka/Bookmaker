package com.dubatovka.app.manager;

@SuppressWarnings("HardCodedStringLiteral")
public final class ConfigConstant {
    //TODO удалить лишние
    //TODO Перенести public static final String в файл с констрантами
    /**
     * User's roles
     */
    public static final String GUEST = "guest";
    public static final String PLAYER = "player";
    public static final String ADMIN = "admin";
    public static final String ANALYST = "analyst";
    
    /**
     * Player's status
     */
    public static final String UNVERIFIED = "unverified";
    public static final String BASIC = "basic";
    public static final String VIP = "vip";
    public static final String BAN = "ban";
    public static final String REQUEST = "request";
    public static final String VERIFIED = "verified";
    
    /**
     * Outcome types
     */
    public static final String TYPE_1 = "1";
    public static final String TYPE_X = "X";
    public static final String TYPE_2 = "2";
    
    /**
     * Bet Status
     */
    public static final String NEW = "new";
    public static final String LOSING = "losing";
    public static final String WIN = "win";
    public static final String PAID = "paid";
    
    /**
     * Locale strings
     */
    public static final String PATH_TO_MESSAGES_BUNDLE = "textcontent/messages";
    public static final String LOCALE_DEFAULT = "default";
    public static final String RU_RU = "ru_RU";
    public static final String EN_US = "en_US";
    public static final String LANG_EN = "en";
    public static final String LANG_RU = "ru";
    public static final String COUNTRY_US = "US";
    public static final String COUNTRY_RU = "RU";
    
    /**
     * Additional string constants
     */
    public static final char QUERY_START_SEPARATOR = '?';
    public static final char VALUE_SEPARATOR = '=';
    public static final char PARAMETER_SEPARATOR = '&';
    public static final char MESSAGE_SEPARATOR = ' ';
    public static final String PERCENT = "%";
    public static final String EMPTY_STRING = "";
    public static final String WHITESPACE = " ";
    public static final String ALL = "all";
    public static final String DOT = ".";
    public static final String RANGE_SPLITERATOR = "\\D+";
    
    /**
     * {@link javax.servlet.http.HttpServletRequest} and {@link javax.servlet.http.HttpSession}
     * attribute common names
     */
    public static final String ATTR_ROLE = "role";
    public static final String ATTR_USER = "user";
    public static final String ATTR_PLAYER = PLAYER;
    public static final String ATTR_ADMIN = ADMIN;
    public static final String ATTR_ANALYST = ANALYST;
    public static final String ATTR_LOCALE = "locale";
    public static final String ATTR_PREV_QUERY = "prevQuery";
    public static final String ATTR_ERROR_MESSAGE = "errorMessage";
    public static final String ATTR_INFO_MESSAGE = "infoMessage";
    public static final String ATTR_SPORT_SET = "sport_set";
    public static final String ATTR_PAGINATION = "pagination";
    public static final String ATTR_PLAYERS = "players";
    public static final String ATTR_EMAIL_INPUT = "email_input";
    public static final String ATTR_FNAME_INPUT = "fname_input";
    public static final String ATTR_LNAME_INPUT = "lname_input";
    public static final String ATTR_MNAME_INPUT = "mname_input";
    public static final String ATTR_BIRTHDATE_INPUT = "birthdate_input";
    public static final String ATTR_EVENT_QUERY_TYPE = "event_query_type";
    public static final String ATTR_EVENT_GOTO_TYPE = "event_goto_type";
    public static final String ATTR_EVENT_SET = "event_set";
    public static final String ATTR_EVENT_COUNT_MAP = "event_count_map";
    public static final String ATTR_TYPE_1_MAP = "type_1_map";
    public static final String ATTR_TYPE_X_MAP = "type_x_map";
    public static final String ATTR_TYPE_2_MAP = "type_2_map";
    public static final String ATTR_CATEGORY = "category";
    public static final String ATTR_SPORT_CATEGORY = "sportCategory";
    public static final String ATTR_EVENT = "event";
    public static final String ATTR_OUTCOME = "outcome";
    public static final String ATTR_CATEGORY_ID = "category_id";
    public static final String ATTR_WIN_BET_COUNT = "win_bet_count";
    public static final String ATTR_WIN_BET_SUM = "win_bet_sum";
    public static final String ATTR_BET_LIST = "bet_list";
    public static final String ATTR_EVENT_MAP = "event_map";
    public static final String ATTR_CATEGORY_MAP = "category_map";
    public static final String ATTR_SPORT_MAP = "sport_map";
    
    /**
     * {@link javax.servlet.http.HttpServletRequest} parameter names
     */
    public static final String PARAM_COMMAND_TYPE = "command_type";
    public static final String PARAM_PAGE_NUMBER = "page_number";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_FNAME = "fname";
    public static final String PARAM_LNAME = "lname";
    public static final String PARAM_LOCALE = "locale";
    public static final String PARAM_MNAME = "mname";
    public static final String PARAM_BIRTHDATE = "birthdate";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_CATEGORY_ID = "category_id";
    public static final String PARAM_PASSWORD_AGAIN = "password_again";
    public static final String PARAM_PASSWORD_OLD = "old_password";
    public static final String PARAM_EVENT_ID = "event_id";
    public static final String PARAM_OUTCOME_TYPE = "outcome_type";
    public static final String PARAM_OUTCOME_COEFFICIENT = "outcome_coefficient";
    public static final String PARAM_BET_AMOUNT = "bet_amount";
    public static final String PARAM_EVENT_EDIT_TYPE = "edit_type";
    public static final String PARAM_OUTCOME_1 = "outcome_1";
    public static final String PARAM_OUTCOME_X = "outcome_X";
    public static final String PARAM_OUTCOME_2 = "outcome_2";
    public static final String PARAM_DATE = "date";
    public static final String PARAM_PARTICIPANT_1 = "participant_1";
    public static final String PARAM_PARTICIPANT_2 = "participant_2";
    public static final String PARAM_RESULT_1 = "result_1";
    public static final String PARAM_RESULT_2 = "result_2";
    
    /**
     * Server message property keys
     */
    public static final String MESSAGE_ERR_BET_AMOUNT_LESS_BALANCE = "err.bet.amount.less.balance";
    public static final String MESSAGE_ERR_BET_AMOUNT_LESS_BET_LIMIT = "err.bet.amount.less.bet.limit";
    public static final String MESSAGE_ERR_BET_FOR_EMPLOYEE = "err.bet.for.employee";
    public static final String MESSAGE_ERR_BET_GOTO_REGISTRATION = "err.bet.goto.registration";
    public static final String MESSAGE_ERR_BET_TIME = "err.bet.time";
    public static final String MESSAGE_ERR_BETTING_INTERRUPTED = "err.betting.interrupted";
    public static final String MESSAGE_ERR_EVENT_CREATE = "err.event.create";
    public static final String MESSAGE_ERR_EVENT_DELETE = "err.event.delete";
    public static final String MESSAGE_ERR_EVENT_UPDATE_INFO = "err.event.update.info";
    public static final String MESSAGE_ERR_INVALID_BET_AMOUNT = "invalid.bet.amount";
    public static final String MESSAGE_ERR_INVALID_BIRTHDATE = "invalid.birthdate";
    public static final String MESSAGE_ERR_INVALID_CATEGORY_ID = "invalid.category.id";
    public static final String MESSAGE_ERR_INVALID_DATE = "invalid.date";
    public static final String MESSAGE_ERR_INVALID_EMAIL = "invalid.email";
    public static final String MESSAGE_ERR_INVALID_EVENT_ID = "invalid.event.id";
    public static final String MESSAGE_ERR_INVALID_EVENT_OUTCOME = "invalid.event.outcome";
    public static final String MESSAGE_ERR_INVALID_EVENT_RESULT = "invalid.event.result";
    public static final String MESSAGE_ERR_INVALID_NAME = "invalid.name";
    public static final String MESSAGE_ERR_INVALID_PARTICIPANT = "invalid.participant";
    public static final String MESSAGE_ERR_INVALID_PASSWORD = "invalid.password";
    public static final String MESSAGE_ERR_INVALID_REQUEST_PARAMETER = "invalid.request.param";
    public static final String MESSAGE_ERR_LOGIN_MISMATCH = "err.login.mismatch";
    public static final String MESSAGE_ERR_OUTCOME_COEFF_CHANGE = "err.outcome.coeff.change";
    public static final String MESSAGE_ERR_OUTCOME_UPDATE = "err.outcome.update";
    public static final String MESSAGE_ERR_PASSWORD_MISMATCH = "err.password.mismatch";
    public static final String MESSAGE_ERR_PAY_WIN_BET = "err.pay.win.bet";
    public static final String MESSAGE_ERR_PLAYER_NOT_DEFINED = "err.player.notdefined";
    public static final String MESSAGE_ERR_PLAYER_STATUS_BAN = "err.player.status.ban";
    public static final String MESSAGE_ERR_SQL_OPERATION = "err.sql.operation";
    public static final String MESSAGE_ERR_SQL_TRANSACTION = "err.sql.transaction";
    public static final String MESSAGE_INF_BET_IS_DONE = "inf.bet.done";
    public static final String MESSAGE_INF_EVENT_CREATE = "inf.event.create";
    public static final String MESSAGE_INF_EVENT_DELETE = "inf.event.delete";
    public static final String MESSAGE_INF_EVENT_UPDATE_INFO = "inf.event.update.info";
    public static final String MESSAGE_INF_OUTCOME_UPDATE = "inf.outcome.update";
    public static final String MESSAGE_INF_PAY_WIN_BET = "inf.pay.win.bet";
    
    /**
     * Navigation response types
     */
    public static final String FORWARD = "forward";
    public static final String REDIRECT = "redirect";
    
    /**
     * Navigation previous query constant
     */
    public static final String PREV_QUERY = "prevQuery";
    
    /**
     * Common JSP pages paths
     */
    public static final String PAGE_INDEX = "/index.jsp";
    public static final String PAGE_MAIN = "/pages/main.jsp";
    public static final String PAGE_REGISTER = "/pages/register.jsp";
    public static final String PAGE_MAKE_BET = "/pages/make_bet.jsp";
    public static final String PAGE_PLAYER_STATE = "/pages/player_state.jsp";
    public static final String PAGE_SUPPORT = "/pages/support.jsp";
    public static final String PAGE_PROFILE = "/pages/profile.jsp";
    public static final String PAGE_VERIFICATION = "/pages/verification.jsp";
    public static final String PAGE_ACCOUNT = "/pages/account.jsp";
    public static final String PAGE_REPLENISH_ACCOUNT = "/pages/replenish_account.jsp";
    public static final String PAGE_WITHDRAW_MONEY = "/pages/withdraw_money.jsp";
    public static final String PAGE_OPERATION_HISTORY = "/pages/operation_history.jsp";
    public static final String PAGE_UPLOAD_PASSPORT = "/pages/upload_passport.jsp";
    public static final String PAGE_RULES = "/pages/rules.jsp";
    public static final String PAGE_ERROR_500 = "/pages/error/error_500.jsp";
    /**
     * Common navigation queries
     */
    public static final String GOTO_INDEX = "/controller?command_type=goto_index";
    public static final String GOTO_MAIN = "/controller?command_type=goto_main";
    public static final String GOTO_PAGINATION = "/controller?command_type=goto_pagination";
    public static final String GOTO_REGISTER = "/controller?command_type=goto_register";
    public static final String GOTO_PLAYER_STATE = "/controller?command_type=goto_player_state";
    public static final String GOTO_SUPPORT = "/controller?command=goto_support";
    public static final String GOTO_PROFILE = "/controller?command=goto_player_profile";
    public static final String GOTO_VERIFICATION = "/controller?command=goto_verification";
    public static final String GOTO_RECOVER_PASSWORD = "/controller?command=goto_recover_password";
    public static final String GOTO_ACCOUNT = "/controller?command=goto_account";
    public static final String GOTO_REPLENISH_ACCOUNT = "/controller?command=goto_replenish_account";
    public static final String GOTO_WITHDRAW_MONEY = "/controller?command=goto_withdraw_money";
    public static final String GOTO_OPERATION_HISTORY = "/controller?command=goto_operation_history";
    public static final String GOTO_UPLOAD_PASSPORT = "/controller?command=goto_upload_passport";
    public static final String GOTO_RULES = "/controller?command=goto_rules";
    public static final String GOTO_ERROR_500 = "/controller?command=goto_error_500";
    public static final String BACK_FROM_ERROR = "/controller?command=back_from_error";
    
    /**
     * Admin JSP pages paths
     */
    public static final String PAGE_MANAGE_PLAYERS = "/pages/manage_players.jsp";
    public static final String PAGE_MANAGE_PLAYER = "/pages/manage_player.jsp";
    public static final String PAGE_MANAGE_SUPPORT = "/pages/manage_support.jsp";
    public static final String PAGE_ANSWER_SUPPORT = "/pages/answer_support.jsp";
    public static final String PAGE_MANAGE_VERIFICATION = "/pages/manage_verification.jsp";
    public static final String PAGE_MANAGE_TRANSACTIONS = "/pages/manage_transactions.jsp";
    public static final String PAGE_STATS_REPORT = "/pages/stats_report.jsp";
    /**
     * Admin navigation queries
     */
    public static final String GOTO_ADMIN_MAIN = "/controller?command=goto_admin_main";
    public static final String GOTO_MANAGE_PLAYER = "/controller?command=goto_manage_player";
    public static final String GOTO_MANAGE_PLAYERS = "/controller?command=goto_manage_players";
    public static final String GOTO_MANAGE_SUPPORT = "/controller?command=goto_manage_support";
    public static final String GOTO_ANSWER_SUPPORT = "/controller?command=goto_answer_support";
    public static final String GOTO_MANAGE_VERIFICATION = "/controller?command=goto_manage_verification";
    public static final String GOTO_MANAGE_TRANSACTIONS = "/controller?command=goto_manage_transactions";
    public static final String GOTO_STATS_REPORT = "/controller?command=goto_manage_report";
    
    /**
     * Main controller constants.
     */
    public static final String MAIN_CONTROLLER = "MainController";
    public static final String MAIN_CONTROLLER_URL = "/controller";
    
    /**
     * Event query types
     */
    public static final String EVENT_QUERY_TYPE_NEW = "new";
    public static final String EVENT_QUERY_TYPE_ACTUAL = "actual";
    public static final String EVENT_QUERY_TYPE_NOT_STARTED = "not_started";
    public static final String EVENT_QUERY_TYPE_STARTED = "started";
    public static final String EVENT_QUERY_TYPE_FAILED = "failed";
    public static final String EVENT_QUERY_TYPE_CLOSED = "closed";
    public static final String EVENT_QUERY_TYPE_TO_PAY = "to_pay";
    
    /**
     * Goto command types for managing events
     */
    public static final String EVENT_GOTO_SHOW_ACTUAL = "show_actual";
    public static final String EVENT_GOTO_SHOW_RESULT = "show_result";
    public static final String EVENT_GOTO_MANAGE_EVENT = "manage_event";
    public static final String EVENT_GOTO_MANAGE_OUTCOME = "manage_outcome";
    public static final String EVENT_GOTO_MANAGE_RESULT = "manage_result";
    public static final String EVENT_GOTO_MANAGE_FAILED = "manage_failed";
    public static final String EVENT_GOTO_SHOW_TO_PAY = "show_to_pay";
    
    /**
     * Keys for maps
     */
    public static final String OUTCOME_TYPE_KEY_NAME = "name";
    public static final String WIN_BET_INFO_KEY_COUNT = "count";
    public static final String WIN_BET_INFO_KEY_SUM = "sum";
    
    /**
     * Pattern for LocalDateTime
     */
    public static final String LOCALE_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm";
    
    public static final String PREFIX_FOR_OUTCOME_TYPE = "TYPE_";
    
    private ConfigConstant() {
    }
}