package com.dubatovka.app.manager;

@SuppressWarnings("HardCodedStringLiteral")
public final class ConfigConstant {
    
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
    public static final String TYPE_X = "x";
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
    public static final String DOT = ".";
    public static final String ALL = "all";
    public static final String RANGE_SPLITERATOR = "\\D+";
    
    /**
     * {@link javax.servlet.http.HttpServletRequest} and {@link javax.servlet.http.HttpSession} attribute common names
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
    
    //TODO вынести все psf поля из команд
    
    /**
     * Attribute names.
     */
    public static final String ATTR_PLAYERS = "players";
    public static final String ATTR_PAGE_MODEL_BUILDER = "page_model_builder";
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
    public static final String ATTR_EDIT_TYPE = "edit_type";
    public static final String ATTR_OUTCOME_MAP = "outcome_map";
    public static final String ATTR_CATEGORY_ID = "category_id";
    
    
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
    /******/
    //TODO удалить лишние
    public static final String PARAM_AMOUNT = "amount";
    public static final String PARAM_ANSWER = "answer";
    public static final String PARAM_BALANCE = "balance";
    public static final String PARAM_BET = "bet";
    
    public static final String PARAM_COMMAND = "command";
    public static final String PARAM_COMMENTARY = "commentary";
    public static final String PARAM_HEADER = "header";
    public static final String PARAM_ID = "id";
    public static final String PARAM_MONTH = "month";
    public static final String PARAM_PASSPORT = "passport";
    public static final String PARAM_SCAN = "scan";
    public static final String PARAM_TARGET = "target";
    public static final String PARAM_FILTER_MY = "show_my";
    public static final String PARAM_SORT_BY_AMOUNT = "sort_by_amount";
    public static final String PARAM_SORT_BY_REST = "sort_by_rest";
    public static final String PARAM_SORT_BY_TOTAL = "sort_by_total";
    public static final String PARAM_STATUS = "status";
    public static final String PARAM_VERIFICATION = "verification";
    public static final String PARAM_WITHDRAWAL = "withdrawal";
    
    /**
     * Server message property keys
     */
    public static final String MESSAGE_INVALID_EMAIL = "invalid.email";
    public static final String MESSAGE_INVALID_NAME = "invalid.name";
    public static final String MESSAGE_INVALID_PASSWORD = "invalid.password";
    public static final String MESSAGE_LOGIN_MISMATCH = "login.mismatch";
    public static final String MESSAGE_PASSWORD_MISMATCH = "password.mismatch";
    public static final String MESSAGE_INVALID_BIRTHDATE = "invalid.birthdate";
    public static final String MESSAGE_ERROR_BET_GOTO_REGISTRATION = "Чтобы сделать ставку, пожалуйста, зарегистрируйтесь и войдите в систему";
    public static final String MESSAGE_ERROR_BET_FOR_EMPLOYEE = "Сотрудники букмекерской компании не могут делать ставки.";
    public static final String MESSAGE_ERROR_BET_AMOUNT_INVALID = "Введенное число не является валидным. Число должно быть больше нуля, меньше либо равно 999.99, число может быть дробным и содержать два знака после запятой.";
    public static final String MESSAGE_ERROR_BET_AMOUNT_LESS_BALANCE = "Введенная сумма ставки превышает баланс на счету игрока. Сделайте ставку меньше.";
    public static final String MESSAGE_ERROR_BET_AMOUNT_LESS_BET_LIMIT = "Введенная сумма ставки превышает лимит по ставкам. Сделайте ставку меньше.";
    public static final String MESSAGE_ERROR_OUTCOME_COEFF = "Коэффициет исхода по выбранному событию изменился с тех пор, как вы решили сделать ставку. Проверьте выбранный коэффициент еще раз.";
    public static final String MESSAGE_ERROR_BET_TIME = "Время, отведенное на ставку, истекло.";
    public static final String MESSAGE_ERROR_BETTING_INTERRUPTED = "Ставка была прервана в процессе осуществления. Вероятно, уже сделана ставка на аналогичный исход события.";
    public static final String MESSAGE_ERROR_INVALID_EVENT_ID = "Невалидный id события.";
    public static final String MESSAGE_ERROR_INVALID_PARTICIPANT = "Невалидный название участника события.";
    public static final String MESSAGE_ERROR_INVALID_DATE = "Невалидная дата события.";
    public static final String MESSAGE_ERROR_INVALID_EVENT_RESULT = "Невалидный результат события.";
    public static final String MESSAGE_ERROR_INVALID_EVENT_OUTCOME = "Невалидный исход события.";
    public static final String MESSAGE_ERROR_INVALID_CATEGORY_ID = "Невалидный id категории.";
    public static final String MESSAGE_ERROR_INVALID_REQUEST_PARAMETER = "Один или несколько параметров запроса не определен.";
    public static final String MESSAGE_ERROR_EVENT_DELETE_FAIL = "Событие не удалось удалить, видимо по нему уже сделаны ставки.";
    public static final String MESSAGE_ERROR_EVENT_CREATE_FAIL = "Событие не удалось создать.";
    public static final String MESSAGE_ERROR_EVENT_UPDATE_INFO_FAIL = "Событие не удалось Обновить.";
    public static final String MESSAGE_ERROR_OUTCOME_UPDATE_FAIL = "Исходы события не удалось Обновить.";
    public static final String MESSAGE_ERROR_SQL_OPERATION = "Database connection error while doing sql operation.";
    public static final String MESSAGE_ERROR_SQL_TRANSACTION = "Database connection error while doing sql transaction. ";
    public static final String MESSAGE_INFO_BET_IS_DONE = "Ставка сделана!";
    public static final String MESSAGE_INFO_EVENT_DELETE_SUCCESS = "Событие успешно удалено.";
    public static final String MESSAGE_INFO_EVENT_CREATE_SUCCESS = "Событие успешно создано.";
    public static final String MESSAGE_INFO_EVENT_UPDATE_INFO_SUCCESS = "Событие успешно обновлено.";
    public static final String MESSAGE_INFO_OUTCOME_UPDATE_SUCCESS = "Исходы событий успешно обновлены.";
    
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
     * Analyst JSP pages paths
     */
    public static final String PAGE_ANALYST_MAIN = "/pages/main.jsp";
    /**
     * Analyst navigation queries
     */
    public static final String GOTO_ANALYST_MAIN = "/controller?command=goto_analyst_main";
    
    /**
     * Other
     */
    public static final String MAIN_CONTROLLER = "MainController";
    public static final String MAIN_CONTROLLER_URL = "/controller";
    
    //TODO Перенести private static final String в файл с констрантами
    /**
     * Otcomes types for events
     */
    public static final String OUTCOME_TYPE_NAME_KEY = "name";
    public static final String OUTCOME_TYPE_1 = "1";
    public static final String OUTCOME_TYPE_X = "X";
    public static final String OUTCOME_TYPE_2 = "2";
    
    /**
     * Event query types
     */
    public static final String EVENT_QUERY_TYPE_NEW = "new";
    public static final String EVENT_QUERY_TYPE_ACTUAL = "actual";
    public static final String EVENT_QUERY_TYPE_NOT_STARTED = "not_started";
    public static final String EVENT_QUERY_TYPE_STARTED = "started";
    public static final String EVENT_QUERY_TYPE_FAILED = "failed";
    public static final String EVENT_QUERY_TYPE_CLOSED = "closed";
    
    /**
     * Goto command types for managing events
     */
    public static final String EVENT_GOTO_SHOW_ACTUAL = "show_actual";
    public static final String EVENT_GOTO_MANAGE = "manage";
    public static final String EVENT_GOTO_SET_COEFFICIENT = "set_coefficient";
    public static final String EVENT_GOTO_CORRECT_COEFFICIENT = "correct_coefficient";
    public static final String EVENT_GOTO_SET_RESULT = "set_result";
    public static final String EVENT_GOTO_SHOW_RESULT = "show_result";
    public static final String EVENT_GOTO_MANAGE_FAILED = "manage_failed";
    
    /**
     * Event edit types
     */
    public static final String EVENT_EDIT_TYPE_DELETE = "delete";
    public static final String EVENT_EDIT_TYPE_CREATE = "create";
    public static final String EVENT_EDIT_TYPE_UPDATE_INFO = "update_info";
    public static final String EVENT_EDIT_TYPE_UPDATE_RESULT = "update_result";
    public static final String EVENT_EDIT_TYPE_UPDATE_OUTCOME = "update_outcome";
    
    public static final String LOCALE_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm";
    
    private ConfigConstant() {
    }
}