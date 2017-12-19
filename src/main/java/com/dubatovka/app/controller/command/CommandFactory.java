package com.dubatovka.app.controller.command;

import com.dubatovka.app.controller.command.impl.ChangeLocaleCommand;
import com.dubatovka.app.controller.command.impl.GotoIndexCommand;
import com.dubatovka.app.controller.command.impl.GotoMain;
import com.dubatovka.app.controller.command.impl.GotoManagePlayersCommand;
import com.dubatovka.app.controller.command.impl.authorization.LoginCommand;
import com.dubatovka.app.controller.command.impl.authorization.LogoutCommand;
import com.dubatovka.app.controller.command.impl.authorization.RegisterCommand;
import com.dubatovka.app.controller.command.navigation.GotoRegisterCommand;
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
    private static final String LOG_FOR_COMMAND_TYPE = "Request doesn't have command_type parameter or defined command_type parameter is invalid: %s. Check web page: %s.";
    private static final String REFERER = "referer";
    
    private static final Map<CommandType, Command> commonCommands = new EnumMap<>(CommandType.class);
    private static final Map<CommandType, Command> guestCommands = new EnumMap<>(CommandType.class);
    private static final Map<CommandType, Command> playerCommands = new EnumMap<>(CommandType.class);
    private static final Map<CommandType, Command> adminCommands = new EnumMap<>(CommandType.class);
    private static final Map<CommandType, Command> analystCommands = new EnumMap<>(CommandType.class);
    
    static {
        commonCommands.put(CommandType.GOTO_INDEX, new GotoIndexCommand());
        commonCommands.put(CommandType.GOTO_MAIN, new GotoMain());
        commonCommands.put(CommandType.GOTO_REGISTER, new GotoRegisterCommand());
        commonCommands.put(CommandType.CHANGE_LOCALE, new ChangeLocaleCommand());
        commonCommands.put(CommandType.REGISTER, new RegisterCommand());
        commonCommands.put(CommandType.LOGIN, new LoginCommand());
        commonCommands.put(CommandType.LOGOUT, new LogoutCommand());
        
        guestCommands.putAll(commonCommands);
        
        playerCommands.putAll(commonCommands);
        
        adminCommands.putAll(commonCommands);
        adminCommands.put(CommandType.GOTO_MANAGE_PLAYERS, new GotoManagePlayersCommand());
        
        analystCommands.putAll(commonCommands);
    }
    
    private enum CommandType {
        REGISTER,
        LOGIN,
        LOGOUT,
        
        CHANGE_LOCALE,
        
        GOTO_MAIN,
        GOTO_INDEX,
        GOTO_REGISTER,
        
        GOTO_MANAGE_PLAYERS,
        GOTO_PAGINATION
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
}