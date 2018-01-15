package com.dubatovka.app.controller;

import com.dubatovka.app.controller.impl.ChangeLocaleCommand;
import com.dubatovka.app.controller.impl.EditEventCommand;
import com.dubatovka.app.controller.impl.MakeBetCommand;
import com.dubatovka.app.controller.impl.authorization.LoginCommand;
import com.dubatovka.app.controller.impl.authorization.LogoutCommand;
import com.dubatovka.app.controller.impl.authorization.RegisterCommand;
import com.dubatovka.app.controller.impl.navigation.GotoIndexCommand;
import com.dubatovka.app.controller.impl.navigation.GotoMainCommand;
import com.dubatovka.app.controller.impl.navigation.GotoMakeBetCommand;
import com.dubatovka.app.controller.impl.navigation.GotoManagePlayersCommand;
import com.dubatovka.app.controller.impl.navigation.GotoPlayerStateCommand;
import com.dubatovka.app.controller.impl.navigation.GotoRegisterCommand;
import com.dubatovka.app.controller.impl.navigation.events.GotoEventCorrectCoefficientCommand;
import com.dubatovka.app.controller.impl.navigation.events.GotoEventManageCommand;
import com.dubatovka.app.controller.impl.navigation.events.GotoEventManageFailedCommand;
import com.dubatovka.app.controller.impl.navigation.events.GotoEventSetCoefficientCommand;
import com.dubatovka.app.controller.impl.navigation.events.GotoEventSetResultCommand;
import com.dubatovka.app.controller.impl.navigation.events.GotoEventShowActualCommand;
import com.dubatovka.app.controller.impl.navigation.events.GotoEventShowResultCommand;
import com.dubatovka.app.entity.User;
import com.dubatovka.app.manager.ConfigConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.EnumMap;
import java.util.Map;

import static com.dubatovka.app.manager.ConfigConstant.ATTR_ROLE;

public final class CommandFactory {
    private static final Logger logger = LogManager.getLogger(CommandFactory.class);
    private static final String LOG_FOR_COMMAND = "Command implementation is not defined for command type: %s. Check class: %s.";
    private static final String LOG_FOR_COMMAND_TYPE = "Request doesn't have command_type parameter or defined command_type parameter is invalid: %s. Check page after request: %s.";
    private static final String REFERER = "referer";
    
    private static final Map<CommandType, Command> commonCommands = new EnumMap<>(CommandType.class);
    private static final Map<CommandType, Command> guestCommands = new EnumMap<>(CommandType.class);
    private static final Map<CommandType, Command> playerCommands = new EnumMap<>(CommandType.class);
    private static final Map<CommandType, Command> adminCommands = new EnumMap<>(CommandType.class);
    private static final Map<CommandType, Command> analystCommands = new EnumMap<>(CommandType.class);
    
    static {
        commonCommands.put(CommandType.GOTO_INDEX, new GotoIndexCommand());
        commonCommands.put(CommandType.GOTO_MAIN, new GotoMainCommand());
        commonCommands.put(CommandType.GOTO_REGISTER, new GotoRegisterCommand());
        commonCommands.put(CommandType.CHANGE_LOCALE, new ChangeLocaleCommand());
        commonCommands.put(CommandType.REGISTER, new RegisterCommand());
        commonCommands.put(CommandType.LOGIN, new LoginCommand());
        commonCommands.put(CommandType.LOGOUT, new LogoutCommand());
        commonCommands.put(CommandType.GOTO_MAKE_BET, new GotoMakeBetCommand());
        commonCommands.put(CommandType.MAKE_BET, new MakeBetCommand());
        commonCommands.put(CommandType.GOTO_EVENT_SHOW_ACTUAL, new GotoEventShowActualCommand());
        commonCommands.put(CommandType.GOTO_EVENT_SHOW_RESULT, new GotoEventShowResultCommand());
        
        guestCommands.putAll(commonCommands);
        
        playerCommands.putAll(commonCommands);
        playerCommands.put(CommandType.GOTO_PLAYER_STATE, new GotoPlayerStateCommand());
        
        adminCommands.putAll(commonCommands);
        adminCommands.put(CommandType.GOTO_MANAGE_PLAYERS, new GotoManagePlayersCommand());
        adminCommands.put(CommandType.GOTO_EVENT_MANAGE, new GotoEventManageCommand());
        adminCommands.put(CommandType.GOTO_EVENT_SET_RESULT, new GotoEventSetResultCommand());
        adminCommands.put(CommandType.GOTO_EVENT_MANAGE_FAILED, new GotoEventManageFailedCommand());
        adminCommands.put(CommandType.EDIT_EVENT, new EditEventCommand());
        
        analystCommands.putAll(commonCommands);
        analystCommands.put(CommandType.GOTO_EVENT_SET_COEFFICIENT, new GotoEventSetCoefficientCommand());
        analystCommands.put(CommandType.GOTO_EVENT_CORRECT_COEFFICIENT, new GotoEventCorrectCoefficientCommand());
        analystCommands.put(CommandType.EDIT_EVENT, new EditEventCommand());
    }
    
    private CommandFactory() {
    }
    
    public static Command defineCommand(HttpServletRequest request) {
        String commandTypeName = request.getParameter(ConfigConstant.PARAM_COMMAND_TYPE);
        User.UserRole role = (User.UserRole) request.getSession().getAttribute(ATTR_ROLE);
        boolean isCommandTypeNameValid = isCommandTypeNameValid(commandTypeName);
        Command command;
        
        if (isCommandTypeNameValid) {
            commandTypeName = commandTypeName.trim().toUpperCase();
            CommandType commandType = CommandType.valueOf(commandTypeName);
            switch (role) {
                case PLAYER:
                    command = playerCommands.get(commandType);
                    break;
                case ADMIN:
                    command = adminCommands.get(commandType);
                    break;
                case ANALYST:
                    command = analystCommands.get(commandType);
                    break;
                case GUEST:
                    command = guestCommands.get(commandType);
                    break;
                default:
                    command = guestCommands.get(commandType);
            }
            
            if (command == null) {
                logger.log(Level.ERROR, String.format(LOG_FOR_COMMAND, commandType, CommandFactory.class.getName()));
                command = new GotoIndexCommand();
            }
        } else {
            logger.log(Level.ERROR, String.format(LOG_FOR_COMMAND_TYPE, commandTypeName, request.getHeader(REFERER)));
            command = new GotoIndexCommand();
        }
        
        return command;
    }
    
    private static boolean isCommandTypeNameValid(String commandName) {
        boolean isExist = !((commandName == null) || commandName.trim().isEmpty());
        if (isExist) {
            for (CommandType type : CommandType.values()) {
                if (type.toString().equalsIgnoreCase(commandName)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private enum CommandType {
        CHANGE_LOCALE,
        
        REGISTER,
        LOGIN,
        LOGOUT,
        
        MAKE_BET,
        
        GOTO_MAIN,
        GOTO_INDEX,
        GOTO_REGISTER,
        
        GOTO_MANAGE_PLAYERS,
        GOTO_MAKE_BET,
        
        GOTO_PLAYER_STATE, GOTO_PAGINATION,
        
        GOTO_EVENT_SHOW_ACTUAL,
        GOTO_EVENT_MANAGE,
        GOTO_EVENT_SET_COEFFICIENT,
        GOTO_EVENT_CORRECT_COEFFICIENT,
        GOTO_EVENT_SET_RESULT,
        GOTO_EVENT_SHOW_RESULT,
        GOTO_EVENT_MANAGE_FAILED,
        
        EDIT_EVENT
    }
}