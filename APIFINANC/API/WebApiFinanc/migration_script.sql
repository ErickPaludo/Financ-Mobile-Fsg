CREATE TABLE IF NOT EXISTS `__EFMigrationsHistory` (
    `MigrationId` varchar(150) CHARACTER SET utf8mb4 NOT NULL,
    `ProductVersion` varchar(32) CHARACTER SET utf8mb4 NOT NULL,
    CONSTRAINT `PK___EFMigrationsHistory` PRIMARY KEY (`MigrationId`)
) CHARACTER SET=utf8mb4;

START TRANSACTION;

ALTER DATABASE CHARACTER SET utf8mb4;

CREATE TABLE `Usuarios` (
    `UserId` int NOT NULL AUTO_INCREMENT,
    `Active` tinyint(1) NOT NULL,
    `Status` int NOT NULL,
    `FirstName` varchar(80) CHARACTER SET utf8mb4 NOT NULL,
    `LastName` varchar(80) CHARACTER SET utf8mb4 NOT NULL,
    `UserName` varchar(15) CHARACTER SET utf8mb4 NOT NULL,
    `UserPass` varchar(15) CHARACTER SET utf8mb4 NOT NULL,
    `Email` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
    CONSTRAINT `PK_Usuarios` PRIMARY KEY (`UserId`)
) CHARACTER SET=utf8mb4;

CREATE TABLE `tb_financ` (
    `Id` int NOT NULL AUTO_INCREMENT,
    `Titulo` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
    `Descricao` varchar(200) CHARACTER SET utf8mb4 NOT NULL,
    `Valor` decimal(15,2) NOT NULL,
    `DthrReg` datetime(6) NOT NULL,
    `DataVencimento` datetime(6) NOT NULL,
    `Parcela` int NOT NULL,
    `Pago` tinyint(1) NOT NULL,
    `Categoria` varchar(1) CHARACTER SET utf8mb4 NOT NULL,
    `UserId` int NOT NULL,
    CONSTRAINT `PK_tb_financ` PRIMARY KEY (`Id`),
    CONSTRAINT `FK_tb_financ_Usuarios_UserId` FOREIGN KEY (`UserId`) REFERENCES `Usuarios` (`UserId`) ON DELETE CASCADE
) CHARACTER SET=utf8mb4;

CREATE INDEX `IX_tb_financ_UserId` ON `tb_financ` (`UserId`);

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20250219011421_Alteracao', '8.0.2');

COMMIT;

