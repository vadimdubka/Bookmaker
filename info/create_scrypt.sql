-- MySQL Script generated by MySQL Workbench
-- 12/22/17 01:01:32
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema bookmaker
-- -----------------------------------------------------
-- Предметная область - букмекерская компания, позволяющая игрокам делать ставки на спортивные события онлайн.
-- 
-- Описание функционала:
-- Систему может использовать 3 вида пользователей: администратор, аналитик и игрок, - каждый из которых имеет свой функционал.
-- 
-- Игрок:
-- - регистрируется в системе.
-- - делает денежные ставки на различные типы исходов спортивных событий (победа, ничья, точный результат и др.), отслеживает результаты по своим ставкам.
-- - подает заявку на верификацию своего аккаунта, что позволит ему снимать денежные средства со счета.
-- - снимает и вносит денежные средства на свой счет.
-- - обладает статусом, от которого зависят его лимиты по ставкам и месячному выводу денежных средств со счета.
-- 
-- Администратор:
-- - управляет пользователями: изменяет роли пользователей, верифицирует аккаунты игроков на основании присланной игроком фотографии паспорта, изменяет статус игроков.
-- - управляет спортивными событиями и категориями событий: добавляет, изменяет, удаляет их.
-- - вносит в систему результаты событий.
-- - переводит на счет пользователей выигранные денежные средства по выигравшим ставкам.
-- 
-- Аналитик:
-- - определяет коэффициент доходности на исходы событий и вносит эту информацию в систему.
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bookmaker` DEFAULT CHARACTER SET utf8 ;
USE `bookmaker` ;

-- -----------------------------------------------------
-- Table `bookmaker`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookmaker`.`category` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id категории.',
  `name` VARCHAR(150) NOT NULL COMMENT 'Наименование категории.',
  `parent_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT 'Id родительской категории для создания древовидной структуры.',
  PRIMARY KEY (`id`),
  INDEX `fk_category_category1_idx` (`parent_id` ASC),
  CONSTRAINT `fk_category_category1`
    FOREIGN KEY (`parent_id`)
    REFERENCES `bookmaker`.`category` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = utf8
COMMENT = 'Категория, в рамках которого существует событие, например, вид спорта или чемпионат по какому-либо спорту.\nКатегории могут иметь древовидную структуру, т.е. в рамках спорта могут проводится различные чемпионаты, в рамках чемпионата могут выделяться различные стадии.';


-- -----------------------------------------------------
-- Table `bookmaker`.`event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookmaker`.`event` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id события',
  `category_id` INT(11) UNSIGNED NOT NULL COMMENT 'Id категории, к которой относится событие.',
  `date` DATETIME NOT NULL COMMENT 'Дата и время начала события.',
  `participant1` VARCHAR(150) NOT NULL COMMENT 'Описание первого или единственного участника события.',
  `participant2` VARCHAR(150) NULL DEFAULT NULL COMMENT 'Описание второго участника события.',
  `result1` SMALLINT(6) NULL DEFAULT NULL COMMENT 'Результат первого участника.',
  `result2` SMALLINT(6) NULL DEFAULT NULL COMMENT 'Результат второго участника.',
  PRIMARY KEY (`id`),
  INDEX `fk_event_category1_idx` (`category_id` ASC),
  CONSTRAINT `fk_event_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `bookmaker`.`category` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 19
DEFAULT CHARACTER SET = utf8
COMMENT = 'Событие в мире спорта, на варианты исхода которого букмекерская контора принимает ставки.';


-- -----------------------------------------------------
-- Table `bookmaker`.`outcome_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookmaker`.`outcome_type` (
  `type` VARCHAR(5) NOT NULL COMMENT 'Идентификатор типа исхода события в виде его краткого обозначения, например, 1 - победа первого участника, X - ничья.',
  `description` VARCHAR(45) NULL DEFAULT NULL COMMENT 'Текстовое описание типа исхода события.',
  PRIMARY KEY (`type`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Возможные типы исходов, которые могут иметь события, например, победа первого участника, ничья, количество забитых голов и т.д.';


-- -----------------------------------------------------
-- Table `bookmaker`.`outcome`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookmaker`.`outcome` (
  `event_id` INT(11) UNSIGNED NOT NULL COMMENT 'Id предстоящего события, для которого определяется вид исхода.',
  `type` VARCHAR(5) NOT NULL COMMENT 'Тип исхода, приминимого к событию.',
  `coefficient` DECIMAL(5,2) NOT NULL COMMENT 'Коэффициент доходности, используемый для расчета размера выигрыша игрока, который букмекер устанавливает на соответствующий тип исхода предстоящего события.',
  PRIMARY KEY (`event_id`, `type`),
  INDEX `fk_outcome_outcome_type1_idx` (`type` ASC),
  CONSTRAINT `fk_outcome_event1`
    FOREIGN KEY (`event_id`)
    REFERENCES `bookmaker`.`event` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_outcome_outcome_type1`
    FOREIGN KEY (`type`)
    REFERENCES `bookmaker`.`outcome_type` (`type`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Исход (результат) события, для которого букмекер рассчитал коэффициент доходности,  и на которое букмекерская компания принимает ставки от игроков.';


-- -----------------------------------------------------
-- Table `bookmaker`.`player_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookmaker`.`player_status` (
  `status` ENUM('unverified', 'basic', 'vip', 'ban') NOT NULL COMMENT 'Статусы игроков:\n- \'unverified\' - присваивается при регистрации новым игрокам. Статус позволяет делать ставки, но запрещает выводить деньги.\n- \'basic\' - присваивается после прохождения верификации. Статус позволяет выводить деньги.\n- ‘vip’ - имеет более высокие лимиты на ставки и вывод денег;\n- ‘ban’ - запрет на возможность делать ставки и выводить деньги.',
  `bet_limit` DECIMAL(5,2) UNSIGNED NOT NULL COMMENT 'Размер максимальной возможной ставки.',
  `withdrawal_limit` DECIMAL(9,2) UNSIGNED NOT NULL COMMENT 'Размер максимальной суммы выведенных средств в месяц.',
  PRIMARY KEY (`status`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Перечисление статусов игроков, влияющий на лимиты по ставкам и выводу денежных средств со счета.';


-- -----------------------------------------------------
-- Table `bookmaker`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookmaker`.`user` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id пользователя.',
  `email` VARCHAR(320) NOT NULL COMMENT 'E-mail пользователя, используемый для входа в систему в качестве логина.\n\nМаксимальная длина email-адреса - 320 символов = 64(local-part)+1(@)+255(domain name).',
  `password` CHAR(32) NOT NULL COMMENT 'Пароль пользователя, зашифрованный MD5.',
  `role` ENUM('player','admin','analyst') NOT NULL DEFAULT 'player' COMMENT 'Роль пользователя: игрок, администратор или аналитик.',
  `registration_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Дата регистрации пользователя.',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC)  COMMENT 'Индекс для ускорения поиска пользователя по его email.')
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8
COMMENT = 'Зарегистрированный пользователь системы.';


-- -----------------------------------------------------
-- Table `bookmaker`.`player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookmaker`.`player` (
  `id` INT(11) UNSIGNED NOT NULL COMMENT 'Id игрока.',
  `player_status` ENUM('unverified', 'basic', 'vip', 'ban') NOT NULL DEFAULT 'unverified' COMMENT 'Стутус игрока. ',
  `balance` DECIMAL(12,2) NOT NULL DEFAULT '10.00' COMMENT 'Размер денежных средств на счету у игрока.',
  `fname` VARCHAR(70) NULL DEFAULT NULL COMMENT 'Имя игрока.',
  `mname` VARCHAR(70) NULL DEFAULT NULL COMMENT 'Отчество игрока.',
  `lname` VARCHAR(70) NULL DEFAULT NULL COMMENT 'Фамилия игрока.',
  `birthday` DATE NULL DEFAULT NULL COMMENT 'Дата рождения игрока. Может применяться для подтверждения, что игроку уже есть 18+ лет.',
  `verification_status` ENUM('unverified', 'request', 'verified') NOT NULL DEFAULT 'unverified' COMMENT 'Статус верификации пользователя.\n\'unverified\' - неверифицированные пользователи.\n\'request\' - пользователь загрузил фотографию и подал заявку на верификацию.\n\'\'verified\' - администратор сверил данные и верифицировал игрока.\n',
  `passport` VARCHAR(45) NULL DEFAULT NULL COMMENT 'Идентификационный номер паспорта игрока. Необходимо для верификации пользователя.',
  `photo` LONGBLOB NULL DEFAULT NULL COMMENT 'Фото паспорта игрока. Необходимо для верификации пользователя.\nИгрок загружает фото паспорта, с помощью которого администратор  может верифицировать игрока и поменять его статус.',
  PRIMARY KEY (`id`),
  INDEX `fk_player_player_status1_idx` (`player_status` ASC),
  CONSTRAINT `fk_player_player_status1`
    FOREIGN KEY (`player_status`)
    REFERENCES `bookmaker`.`player_status` (`status`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_player_user2`
    FOREIGN KEY (`id`)
    REFERENCES `bookmaker`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Игрок, делающий ставки на исходы событий.\nТаблица \"player\" создана со связью \"1-1\" к таблице \"user\", т.к.:\n1)  Часть данных в таблице \"player\" специфична только для игроков, например, свойство \"player_status\" нельзя применить к пользователям с ролью администратор. Т.е. можно рассматривать игрока и пользователя как две отдельные, хоть и взаимосвязанные через наследование сущности.\n2) Если таблицы не разделять, то будет нарушаться 3 нормальная форма, т.к. значение свойства \"player_status\" зависило бы не только от первичного ключа, но и от значения свойства \"role\".';


-- -----------------------------------------------------
-- Table `bookmaker`.`bet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookmaker`.`bet` (
  `player_id` INT(11) UNSIGNED NOT NULL COMMENT 'Id игрока, сделавшего ставку.',
  `event_id` INT(11) UNSIGNED NOT NULL COMMENT 'Id события, по которому игрок сделал ставку.',
  `type` VARCHAR(5) NOT NULL COMMENT 'Тип исхода события,  по которому игрок сделал ставку.',
  `date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Дата и время, когда игрок сделал ставку.',
  `amount` DECIMAL(9,2) NOT NULL COMMENT 'Размер ставки игрока.',
  `status` ENUM('new', 'losing', 'win', 'paid') NOT NULL DEFAULT 'new' COMMENT 'Статус ставки.\n\'new\' - игрок сделал новую ставку на исход события.\n\'losing\' - результаты события известны и ставка проиграла.\n\'win\' -  результаты события известны и ставка выиграла, но еще не оплачена букмекерской конторой.\n\'paid\' - выигравшая ставка оплачена.',
  PRIMARY KEY (`player_id`, `event_id`, `type`),
  INDEX `fk_bet_player1_idx` (`player_id` ASC),
  INDEX `fk_bet_outcome1_idx` (`event_id` ASC, `type` ASC),
  CONSTRAINT `fk_bet_outcome1`
    FOREIGN KEY (`event_id` , `type`)
    REFERENCES `bookmaker`.`outcome` (`event_id` , `type`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_bet_player1`
    FOREIGN KEY (`player_id`)
    REFERENCES `bookmaker`.`player` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Ставка, сделанная игроком на исход события.';


-- -----------------------------------------------------
-- Table `bookmaker`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookmaker`.`transaction` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id операции со счетом',
  `player_id` INT(11) UNSIGNED NOT NULL COMMENT 'Id игрока.',
  `date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Дата операции со счетом.',
  `amount` DECIMAL(9,2) NOT NULL COMMENT 'Сумма по операции со счетом.',
  PRIMARY KEY (`id`),
  INDEX `fk_transaction_player1_idx` (`player_id` ASC),
  CONSTRAINT `fk_transaction_player1`
    FOREIGN KEY (`player_id`)
    REFERENCES `bookmaker`.`player` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Операция по снятию или зачилению денежных средств на счет игрока.';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
