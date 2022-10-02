package homework.microprocessors.db;

import homework.microprocessors.beans.ClockSpeed;
import homework.microprocessors.beans.Microprocessor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MicroprocessorControl {
    public static ArrayList<Microprocessor> getAllMicroprocessors() {
        // открываем соединение
        try (Connection connection = DBConnection.openDBConnection()) {
            // 1. создать выражение
            assert connection != null;
            Statement statement = connection.createStatement();
            // 2. выполнить запрос
            ResultSet queryResult = statement.executeQuery("SELECT * FROM microprocessors");
            // 3. Перебор результатов
            HashMap<Integer, Microprocessor> microprocessorsMap = new HashMap<>();
            while (queryResult.next()) {
                int id = queryResult.getInt(1);
                String model = queryResult.getString(2);
                int dataBitDepth = queryResult.getInt(3);
                int addressBitDepth = queryResult.getInt(4);
                long addressSpaces = queryResult.getLong(5);
                Integer numberOfCommands = queryResult.getObject(6, Integer.class);
                int numberOfElements = queryResult.getInt(7);
                int releaseYear = queryResult.getInt(8);

                Microprocessor microprocessor = new Microprocessor(id, model, dataBitDepth, addressBitDepth,
                        addressSpaces, numberOfCommands, numberOfElements, releaseYear);
                microprocessorsMap.put(id, microprocessor);
            }
            // 3. добавить тактовую частоту в информацию о микроспроцессоре
            queryResult = statement.executeQuery("SELECT * FROM mircroprocessorsClockSpeed AS M " +
                    "   JOIN clockSpeed AS CS ON CS.Id = M.ClockSpeedId");
            while (queryResult.next()) {
                int microprocessorId = queryResult.getInt(2);
                double minValue = queryResult.getDouble(5);
                Double maxValue = queryResult.getObject(6, Double.class);
                ClockSpeed clockSpeed = new ClockSpeed(minValue, maxValue);
                microprocessorsMap.get(microprocessorId).addClockSpeed(clockSpeed);
            }

            // 4. вернуть результат
            Collection<Microprocessor> values = microprocessorsMap.values();
            return new ArrayList<>(values);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Microprocessor getMicroprocessorById(int selectId) {
        // открываем соединение
        try (Connection connection = DBConnection.openDBConnection()) {
            // выполнение ПАРАМЕТРИЗИРОВАННОГО запроса
            // (защищен от SQL-инъекции)

            // сделали строку запроса
            String queryString = "SELECT * FROM microprocessors WHERE Id=?";
            // создали параметризированное выражение
            PreparedStatement preparedStatement = connection.prepareStatement(queryString);
            // проставить параметры
            preparedStatement.setInt(1, selectId);
            // выполнить запрос
            ResultSet queryResult = preparedStatement.executeQuery();
            Microprocessor microprocessor = null;

            if (queryResult.next()) {
                int id = queryResult.getInt(1);
                String model = queryResult.getString(2);
                int dataBitDepth = queryResult.getInt(3);
                int addressBitDepth = queryResult.getInt(4);
                long addressSpaces = queryResult.getLong(5);
                Integer numberOfCommands = queryResult.getObject(6, Integer.class);
                int numberOfElements = queryResult.getInt(7);
                int releaseYear = queryResult.getInt(8);
                microprocessor = new Microprocessor(id, model, dataBitDepth, addressBitDepth,
                        addressSpaces, numberOfCommands, numberOfElements, releaseYear);
            }

            // сделали строку запроса
            queryString = "SELECT * FROM mircroprocessorsClockSpeed AS MCS JOIN clockSpeed AS CS " +
                    "ON CS.Id = MCS.ClockSpeedId WHERE MCS.MicroprocessorId=?";
            // создали параметризированное выражение
            preparedStatement = connection.prepareStatement(queryString);
            // проставить параметры
            preparedStatement.setInt(1, selectId);
            // выполнить запрос
            queryResult = preparedStatement.executeQuery();

            while (queryResult.next()) {
                double minValue = queryResult.getDouble(5);
                Double maxValue = queryResult.getObject(6, Double.class);
                ClockSpeed clockSpeed = new ClockSpeed(minValue, maxValue);
                assert microprocessor != null;
                microprocessor.addClockSpeed(clockSpeed);
            }
            // 4. вернуть результат
            return microprocessor;
        } catch (SQLException e) {
            return null;
        }
    }

    //установить параметры кроме Id
    public static void assignmentInfo(PreparedStatement preparedStatement, Microprocessor microprocessor) throws SQLException {
        preparedStatement.setString(1, microprocessor.getModel());
        preparedStatement.setInt(2, microprocessor.getDataBitDepth());
        preparedStatement.setInt(3, microprocessor.getAddressBitDepth());
        preparedStatement.setLong(4, microprocessor.getAddressSpaces());
        preparedStatement.setObject(5, microprocessor.getNumberOfCommands());
        preparedStatement.setInt(6, microprocessor.getNumberOfElements());
        preparedStatement.setInt(7, microprocessor.getReleaseYear());
    }

    public static void create(Microprocessor microprocessor) {
        try (Connection connection = DBConnection.openDBConnection()) {
            String queryString = "INSERT INTO `microprocessors` (`model`, `dataBitDepth`, `addressBitDepth`, " +
                    "`addressSpaces`, `numberOfCommands`, `numberOfElements`, `releaseYear`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(queryString);
            assignmentInfo(preparedStatement, microprocessor);

            preparedStatement.executeUpdate();

            //получить id созданной записи в базе
            queryString = "SELECT M.`Id` FROM microprocessors AS M WHERE M.`model`=?";
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setString(1, microprocessor.getModel());
            ResultSet queryResult = preparedStatement.executeQuery();

            int id;
            if (queryResult.next())
                 id = queryResult.getInt(1);
            else
                throw new RuntimeException("Тактовая частота не смогла добавиться в базу");

            updateClockSpeed(connection, id, microprocessor.getClockSpeeds());
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void update(Microprocessor microprocessor) {
        try (Connection connection = DBConnection.openDBConnection()) {
            String queryString = "UPDATE microprocessors SET model = ?, dataBitDepth = ?, addressBitDepth = ?, " +
                    "addressSpaces = ?, numberOfCommands = ?, numberOfElements = ?, releaseYear = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(queryString);
            // проставить параметры
            assignmentInfo(preparedStatement, microprocessor);
            preparedStatement.setInt(8, microprocessor.getId());
            // выполнить запрос
            preparedStatement.executeUpdate();

            updateClockSpeed(connection, microprocessor.getId(),microprocessor.getClockSpeeds());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateClockSpeed(Connection connection, int id, ArrayList<ClockSpeed> clockSpeeds) throws SQLException {
        //удалить записи с расшивочной таблицы, указывающих на процессор
        String queryString = "DELETE FROM `mircroprocessorsClockSpeed` WHERE `MicroprocessorId`=?";
        PreparedStatement preparedStatement = connection.prepareStatement(queryString);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

        for (ClockSpeed clockSpeed : clockSpeeds) {
            //проверить есть ли в базе сочетание min и max
            double min = clockSpeed.getMin();
            Double max = clockSpeed.getMax();

            if (max == null) {
                queryString = "SELECT * FROM clockSpeed AS CS WHERE CS.MinValue=? AND CS.MaxValue IS NULL";
                preparedStatement = connection.prepareStatement(queryString);
            } else {
                queryString = "SELECT * FROM clockSpeed AS CS WHERE CS.MinValue=? AND CS.MaxValue=?";
                preparedStatement = connection.prepareStatement(queryString);
                preparedStatement.setDouble(2, max);
            }
            preparedStatement.setDouble(1, min);
            ResultSet queryResult = preparedStatement.executeQuery();

            //если нет, то добавить
            if (!queryResult.next()) {
                queryString = "INSERT INTO `clockSpeed` (`MinValue`, `MaxValue`) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(queryString);
                preparedStatement.setDouble(1, min);
                if (max == null)
                    preparedStatement.setNull(2, Types.FLOAT);
                else
                    preparedStatement.setDouble(2, max);
                preparedStatement.executeUpdate();
            }

            //добавить значения в расшивочную таблицу
            if (max == null) {
                queryString = "INSERT INTO `mircroprocessorsClockSpeed` (`MicroprocessorId`, `ClockSpeedId`) " +
                        "VALUES (?, (SELECT `Id` FROM `clockSpeed` WHERE `MinValue`=? AND `MaxValue` IS NULL))";
                preparedStatement = connection.prepareStatement(queryString);
            } else {
                queryString = "INSERT INTO `mircroprocessorsClockSpeed` (`MicroprocessorId`, `ClockSpeedId`) " +
                        "VALUES (?, (SELECT `Id` FROM `clockSpeed` WHERE `MinValue`=? AND `MaxValue`=?))";;
                preparedStatement = connection.prepareStatement(queryString);
                preparedStatement.setDouble(3, max);
            }
            preparedStatement.setInt(1, id);
            preparedStatement.setDouble(2, min);
            preparedStatement.executeUpdate();
        }
    }

    public static void delete(int id) {
        try (Connection connection = DBConnection.openDBConnection()) {
            String queryString = "DELETE FROM `mircroprocessorsClockSpeed` WHERE `MicroprocessorId`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            queryString = "DELETE FROM `microprocessors` WHERE `Id`=?";
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
}
