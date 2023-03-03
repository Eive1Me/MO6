import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //TODO Задача 1.
        //считываем данные
        MatrixFractional firstTargetMatrix = new MatrixFractional();
        Utils.inputFirstTaskData(firstTargetMatrix);
        System.out.println("\nЗадание 1. \nНайти верхнюю и нижнюю цены игры и седловую точку.\n\nИсходная матрица:");
        System.out.println(firstTargetMatrix);
        MatrixFractional firstMatrix = new MatrixFractional(firstTargetMatrix);
        //Считаем минимум по строкам и максимум по столбцам
        firstMatrix.countMinAndMax();
        System.out.println("Таблица с минимумами:");
        System.out.println(firstMatrix);
        //Максимум минимумов и минимум максимов
        Double maxInLastColumn1 = firstMatrix.maxInColumnValue("x" + firstMatrix.width(), new ArrayList<>());
        System.out.println("Нижняя цена игры (максимум из нового столбца): " + maxInLastColumn1);
        Double minInLastRow1 = firstMatrix.minInRowValue("y" + firstMatrix.height(), new ArrayList<>());
        System.out.println("Верхняя цена игры (минимум из новой строки): " + minInLastRow1);
        //Седловая точка
        if (maxInLastColumn1.equals(minInLastRow1)) {
            for (String columnName : firstTargetMatrix.columnNames()) {
                for (String rowName : firstTargetMatrix.rowNames()) {
                    Cell cell = new Cell(columnName, rowName);
                    if (firstMatrix.get(cell).equals(maxInLastColumn1)) {
                        System.out.println("Её координаты (" + cell.getColumn() + "; " + cell.getRow() + ") и значение в ней " + maxInLastColumn1);
                        return;
                    }
                }
            }
        } else {
            System.out.println("Седловой точки нет");
        }

        System.out.println("o-------------------------------------------------o");
        //TODO Задача 2.
        //считываем данные
        MatrixFractional secondTargetMatrix = new MatrixFractional();
        Utils.inputSecondTaskData(secondTargetMatrix);
        System.out.println("\nЗадание 2. \nНайти решения в смешанных стратегиях.\n\nИсходная матрица:");
        System.out.println(secondTargetMatrix);
        MatrixFractional secondMatrix = new MatrixFractional(secondTargetMatrix);
        secondMatrix.countMinAndMax();
        System.out.println("Таблица с минимумами:");
        System.out.println(secondMatrix);

        Double maxInLastColumn2 = secondMatrix.maxInColumnValue("x" + secondMatrix.width(), new ArrayList<>());
        System.out.println("Нижняя цена игры (максимум из нового столбца): " + maxInLastColumn2);
        Double minInLastRow2 = secondMatrix.minInRowValue("y" + secondMatrix.height(), new ArrayList<>());
        System.out.println("Верхняя цена игры (минимум из новой строки): " + minInLastRow2);

        if (maxInLastColumn2.equals(minInLastRow2)) {
            System.out.println("Есть седловая точка. Задание неправильное");
        } else {
            System.out.println("Седловой точки нет\n");
        }

        int height = secondMatrix.height(), width = secondMatrix.width();
        secondMatrix.removeRow("y" + height);
        secondMatrix.removeColumn("x" + width);
        height--;
        width--;
        // В доминирующая строка - та в которой наим знач, столбец - наибольшие.
        System.out.println("Проверка на наличие доминирующих столбцов и строк:");
        List<String> rowNames = secondMatrix.rowNames();
        for (String dominatedRowName : rowNames) {
            for (String obeyRowName : rowNames) {
                if (isDominatedRow(secondMatrix, dominatedRowName, obeyRowName)) {
                    secondMatrix.removeRow(obeyRowName);
                    System.out.println("Убрали строку " + obeyRowName);
                }
            }
        }
        List<String> columnNames = secondMatrix.columnNames();
        for (String dominatedColumnName : columnNames) {
            for (String obeyColumnName : columnNames) {
                if (isDominatedColumn(secondMatrix, dominatedColumnName, obeyColumnName)) {
                    secondMatrix.removeColumn(obeyColumnName);
                    System.out.println("Убрали столбец " + obeyColumnName);
                }
            }
        }
        if (height == secondMatrix.height() && width == secondMatrix.width()) System.out.println("\nДоминирующих элементов не найдено.");
        System.out.println();
        System.out.println("Матрица без доминирующих элементов:");
        System.out.println(secondMatrix);

        System.out.println("Применим симплекс-метод:");
        MatrixFractional simplexedMatrix = simplex(secondMatrix);

        //Считаем оптимальные планы (берем из таблицы)
        List<Double> xList = new ArrayList<>();
        for (int i = 0; i < simplexedMatrix.height() - 1; ++i) {
            Cell cell = new Cell("C", "z" + (i + 1));
            if (simplexedMatrix.get(cell) != null) {
                xList.add(simplexedMatrix.get(cell));
            } else {
                xList.add(0.0);
            }
        }
        System.out.println("Все x (значения из столбца C):");
        System.out.println(xList);

        List<Double> yList = new ArrayList<>();
        for (int i = 0; i < (simplexedMatrix.width() - 1) / 2; ++i) {
            Cell cell = new Cell("z" + ((i + (simplexedMatrix.width() - 1) / 2) % (simplexedMatrix.width() - 1) + 1), "Fun");
            yList.add(simplexedMatrix.get(cell));
        }
        System.out.println("Все y (значения из строки Fun):");
        System.out.println(yList);

        //Это сумма соответствующих координат оптимальных планов
        System.out.println("Линейная форма оптимальных планов (сумма x-ов): " + sumList(xList));
        System.out.println("Линейная форма оптимальных планов (сумма y-ов): " + sumList(yList));

        //Цена игры
        double gameCost = 1 / sumList(xList);
        System.out.println("Цена игры: " + gameCost);

        //Цена игры * все х и все у
        System.out.println("Оптимальная смешанная стратегия 1 игрока: " + multiplyList(yList, gameCost));
        System.out.println("Оптимальная смешанная стратегия 2 игрока: " + multiplyList(xList, gameCost));


        System.out.println("\no------------------------------------------------------------------------------------------------------o");
        //TODO Задача 3.
        //считываем данные
        MatrixFractional thirdTargetMatrix = new MatrixFractional();
        Utils.inputSecondTaskData(thirdTargetMatrix);
        System.out.println("\nЗадание 3. \nДля матрицы, приведенной в задании 2, реализовать метод фиктивного розыгрыша Брауна–Робинсона.\n\nИсходная матрица:");
        System.out.println(thirdTargetMatrix);

        MatrixFractional braunMatrix = new MatrixFractional();
        List<String> forCalcX = new ArrayList<>();
        List<String> forCalcY = new ArrayList<>();

        forCalcX.add("i");
        forCalcY.add("i");
        forCalcX.add("j");
        forCalcY.add("j");
        forCalcX.add("Vmin");
        forCalcY.add("Vmin");
        forCalcX.add("Vmax");
        forCalcY.add("Vmax");
        forCalcX.add("V*");
        forCalcY.add("V*");

        // Первую итерацию вручную
        braunMatrix.set(new Cell("i", "1"), 1.0);
        for (String columnName : thirdTargetMatrix.columnNames()) {
            braunMatrix.set(new Cell(columnName, "1"), thirdTargetMatrix.get(new Cell(columnName, "y1")));
            forCalcY.add(columnName);
        }
        int firstJValue = Integer.parseInt(dumpFirstLetter(braunMatrix.minInRowCell("1", forCalcX).getColumn()));
        braunMatrix.set(new Cell("j", "1"), (double) firstJValue);
        for (String rowName : thirdTargetMatrix.rowNames()) {
            braunMatrix.set(new Cell(rowName, "1"), thirdTargetMatrix.get(new Cell("x" + firstJValue, rowName)));
            forCalcX.add(rowName);
        }
        braunMatrix.set(new Cell("Vmin", "1"), braunMatrix.minInRowValue("1", forCalcX));
        braunMatrix.set(new Cell("Vmax", "1"), braunMatrix.maxInRowValue("1", forCalcY));
        braunMatrix.set(new Cell("V*", "1"), (braunMatrix.minInRowValue("1", forCalcX) + braunMatrix.maxInRowValue("1", forCalcY)) / 2);


        // Все остальные итерации
        for (int i = 2; i < 1000; ++i) {
            Cell maxYInPreviousIteration = braunMatrix.maxInRowCell(String.valueOf(i - 1), forCalcY);
            int currentMatrixRowNumber = Integer.parseInt(dumpFirstLetter(maxYInPreviousIteration.getColumn()));
            braunMatrix.set(new Cell("i", String.valueOf(i)), (double) currentMatrixRowNumber);

            for (String columnName : thirdTargetMatrix.columnNames()) {
                braunMatrix.set(new Cell(columnName, String.valueOf(i)),
                        braunMatrix.get(new Cell(columnName, String.valueOf(i - 1))) +
                                thirdTargetMatrix.get(new Cell(columnName, "y" + currentMatrixRowNumber)));
            }

            int currentMatrixColumnNumber = Integer.parseInt(dumpFirstLetter(
                    braunMatrix.minInRowCell(String.valueOf(i), forCalcX).getColumn()));
            braunMatrix.set(new Cell("j", String.valueOf(i)), (double) currentMatrixColumnNumber);

            for (String rowName : thirdTargetMatrix.rowNames()) {
                braunMatrix.set(new Cell(rowName, String.valueOf(i)),
                        braunMatrix.get(new Cell(rowName, String.valueOf(i - 1))) +
                                thirdTargetMatrix.get(new Cell("x" + currentMatrixColumnNumber, rowName)));
            }

            braunMatrix.set(new Cell("Vmin", String.valueOf(i)), braunMatrix.minInRowValue(String.valueOf(i), forCalcX) / i);
            braunMatrix.set(new Cell("Vmax", String.valueOf(i)), braunMatrix.maxInRowValue(String.valueOf(i), forCalcY) / i);
            braunMatrix.set(new Cell("V*", String.valueOf(i)), (braunMatrix.minInRowValue(String.valueOf(i), forCalcX) / i + braunMatrix.maxInRowValue(String.valueOf(i), forCalcY) / i) / 2);

        }



        System.out.println(braunMatrix.toStringShort());

        Map<Double, Integer> xArr = new HashMap<>();
        Map<Double, Integer> yArr = new HashMap<>();

        //Считаем сколько раз использовались стратегии
        for (String rowNames1 : braunMatrix.rowNames()) {
            double xKey = braunMatrix.get(new Cell("i", rowNames1));
            if (xArr.containsKey(xKey)) {
                xArr.put(xKey, xArr.get(xKey) + 1);
            } else {
                xArr.put(xKey, 1);
            }

            double yKey = braunMatrix.get(new Cell("j", rowNames1));
            if (yArr.containsKey(yKey)) {
                yArr.put(yKey, yArr.get(yKey) + 1);
            } else {
                yArr.put(yKey, 1);
            }
        }

        for (Double key : xArr.keySet()) {
            System.out.println("A" + String.valueOf(key).charAt(0) + ": " + ((double) xArr.get(key) / braunMatrix.height()));
        }
        System.out.println();
        for (Double key : yArr.keySet()) {
            System.out.println("B" + String.valueOf(key).charAt(0) + ": " + ((double) yArr.get(key) / braunMatrix.height()));
        }
    }

    private static String dumpFirstLetter(String s) {
        return s.substring(1);
    }

    private static boolean isDominatedRow(MatrixFractional matrix, String dominatedRow, String obeyRow) {
        boolean answer = false;
        for (String columnName : matrix.columnNames()) {
            if ((matrix.get(new Cell(columnName, dominatedRow)) == null) || (matrix.get(new Cell(columnName, obeyRow)) == null)) {
                return false;
            }
            if (matrix.get(new Cell(columnName, dominatedRow)) < matrix.get(new Cell(columnName, obeyRow))) {
                return false;
            }
            if (matrix.get(new Cell(columnName, dominatedRow)) > matrix.get(new Cell(columnName, obeyRow))) {
                answer = true;
            }
        }
        return answer;
    }

    private static boolean isDominatedColumn(MatrixFractional matrix, String dominatedColumn, String obeyColumn) {
        boolean answer = false;
        for (String rowName : matrix.rowNames()) {
            if ((matrix.get(new Cell(dominatedColumn, rowName)) == null) || (matrix.get(new Cell(obeyColumn, rowName)) == null)) {
                return false;
            }
            if (matrix.get(new Cell(dominatedColumn, rowName)) > matrix.get(new Cell(obeyColumn, rowName))) {
                return false;
            }
            if (matrix.get(new Cell(dominatedColumn, rowName)) < matrix.get(new Cell(obeyColumn, rowName))) {
                answer = true;
            }
        }
        return answer;
    }

    private static MatrixFractional simplex(MatrixFractional targetMatrix) {
        MatrixFractional table = genSimplexTable(targetMatrix);
        System.out.println();
        System.out.println("Изначальная симплекс-таблица:");
        System.out.println(table);

        int iteration = 0;
        while (table.minInColumnValue("C", List.of("Fun")) < 0.0) {
            Cell minInCCell = table.minInColumnCell("C", List.of("Fun"));
            System.out.println("Минимальный элемент среди свободных членов: " + table.get(minInCCell));
            System.out.println("Среди свободных членов есть отрицательные. Нужно перейти к допустимому решению");

            Cell minCellInRowWithMinC = table.minInRowCell(minInCCell.getRow(), List.of("C"));
            if (table.get(minCellInRowWithMinC) >= 0.0) {
                System.out.println("Задачу решить нельзя");
                System.exit(0);
            }
            System.out.println("Ведущая строка: " + minCellInRowWithMinC.getRow());
            System.out.println("Ведущий столбец: " + minCellInRowWithMinC.getColumn());
            System.out.println();

            ++iteration;
            System.out.println("Пересчитываем таблицу. Итерация " + iteration);
            table = recalc(table, minCellInRowWithMinC);
            table = checkZero(table);
            System.out.println(table);
        }

        // У целевой функции не должно быть отрицательных элементов
        iteration = 0;
        while (table.minInRowValue("Fun", List.of("C")) < 0.0) {
            ++iteration;
            System.out.println("У целевой функции есть отрицательные элементы. Избавимся. Итерация " + iteration);

            // Разрешающий столбец
            Cell baseColumnCell = table.minInRowCell("Fun", List.of("C"));
            String rowPair = null;
            Double minDiv = null;

            // Вычисляем разрешающую строку
            for (String rowName : table.rowNames()) {
                if (table.get(new Cell(baseColumnCell.getColumn(), rowName)) > 0.0) {
                    if (rowPair == null) {
                        rowPair = rowName;
                        minDiv = table.get(new Cell("C", rowName)) / table.get(new Cell(baseColumnCell.getColumn(), rowName));
                    } else {
                        if (table.get(new Cell("C", rowName)) / table.get(new Cell(baseColumnCell.getColumn(), rowName)) < minDiv) {
                            rowPair = rowName;
                            minDiv = table.get(new Cell("C", rowName)) / table.get(new Cell(baseColumnCell.getColumn(), rowName));
                        }
                    }
                }
            }
            if (rowPair == null) {
                System.out.println("Посчитать не получится");
                System.exit(0);
            }

            System.out.println("Разрешающий столбец: " + baseColumnCell.getColumn());
            System.out.println("Разрешающая строка: " + rowPair);
            Cell baseCell = new Cell(baseColumnCell.getColumn(), rowPair);

            table = recalc(table, baseCell);
            System.out.println(table);
        }
        return table;
    }

    private static MatrixFractional recalc(MatrixFractional targetMatrix, Cell baseCell) {
        MatrixFractional table = new MatrixFractional(targetMatrix);

        List<String> columnNames = targetMatrix.columnNames();
        List<String> rowNames = targetMatrix.rowNames();

        // Главную строку и столбец пересчитаем потом, потому что они используются для подсчётов
        for (String columnName : columnNames) {
            for (String rowName : rowNames) {
                if ((!columnName.equals(baseCell.getColumn())) && (!rowName.equals(baseCell.getRow()))) {
                    Cell cell = new Cell(columnName, rowName);
                    double coefficient = targetMatrix.get(new Cell(baseCell.getColumn(), cell.getRow())) /
                            targetMatrix.get(new Cell(baseCell.getColumn(), baseCell.getRow()));
                    double value = targetMatrix.get(cell) - coefficient * targetMatrix.get(new Cell(cell.getColumn(), baseCell.getRow()));
                    checkZero(table);
                    table.set(cell, value);
                }
            }
        }

        // Главный столбец без главной строки
        for (String rowName : rowNames) {
            if (!rowName.equals(baseCell.getRow())) {
                table.set(new Cell(baseCell.getColumn(), rowName), 0.0);
            }
        }

        // Главная строка
        for (String columnName : columnNames) {
            double value = targetMatrix.get(new Cell(columnName, baseCell.getRow())) / targetMatrix.get(baseCell);
            table.set(new Cell(columnName, baseCell.getRow()), value);
        }

        // Меняем заголовок строки
        table.changeRowName(baseCell.getRow(), baseCell.getColumn());

        checkZero(table);
        return table;
    }

    // Удостоверимся, что нет -0.0
    private static MatrixFractional checkZero(MatrixFractional targetMatrix) {
        MatrixFractional matrix = new MatrixFractional(targetMatrix);
        List<String> columnNames = matrix.columnNames();
        List<String> rowNames = matrix.rowNames();

        for (String columnName : columnNames) {
            for (String rowName : rowNames) {
                Cell cell = new Cell(columnName, rowName);
                if (Math.abs(matrix.get(cell)) <= 0.000001) {
                    matrix.set(cell, 0.0);
                }
            }
        }

        return matrix;
    }

    private static double sumList(List<Double> list) {
        double answer = 0.0;
        for (double value : list) {
            answer += value;
        }
        return answer;
    }

    private static List<Double> multiplyList(List<Double> list, double value) {
        List<Double> answer = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            answer.add(list.get(i) * value);
        }
        return answer;
    }

    private static MatrixFractional genSimplexTable(MatrixFractional targetMatrix) {
        MatrixFractional table = new MatrixFractional();

        List<String> columnNames = targetMatrix.columnNames();
        List<String> rowNames = targetMatrix.rowNames();

        for (int row = 0; row < rowNames.size(); ++row) {
            Cell simplexTableCell = new Cell("C", "z" + (row + targetMatrix.width() + 1));
            table.set(simplexTableCell, 1.0);
        }
        table.set(new Cell("C", "Fun"), 0.0);

        for (int column = 0; column < columnNames.size(); ++column) {
            for (int row = 0; row < rowNames.size(); ++row) {
                Cell targetMatrixCell = new Cell(columnNames.get(column), rowNames.get(row));
                Cell simplexTableCell = new Cell("z" + (column + 1), "z" + (row + targetMatrix.width() + 1));
                table.set(simplexTableCell, targetMatrix.get(targetMatrixCell));
            }
            Cell targetMatrixCellFun = new Cell("z" + (column + 1), "Fun");
            table.set(targetMatrixCellFun, -1.0);
        }

        for (int column = columnNames.size(); column < columnNames.size() + rowNames.size(); ++column) {
            for (int row = 0; row < rowNames.size(); ++row) {
                Cell simplexTableCell = new Cell("z" + (column + 1), "z" + (row + targetMatrix.width() + 1));
                if (row == column - columnNames.size()) {
                    table.set(simplexTableCell, 1.0);
                } else {
                    table.set(simplexTableCell, 0.0);
                }
            }
            Cell targetMatrixCellFun = new Cell("z" + (column + 1), "Fun");
            table.set(targetMatrixCellFun, 0.0);
        }

        return table;
    }
}
