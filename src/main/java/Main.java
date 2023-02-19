public class Main {
    public static void main(String[] args) {
        //TODO Задача 1.
        //считываем данные
        Matrix matrix = new Matrix(new double[][]{{2,0,1,4},
                                               {1,2,5,3},
                                               {4,1,3,2}});
        System.out.println("\nЗадание 1. \nНайти верхнюю и нижнюю цены игры и седловую точку.\n\nИсходная матрица:");
        System.out.println(matrix);
        System.out.println("Седловая точка: " + matrix.saddlePoint());
        System.out.println("Нижняя цена: " + matrix.lowerCost());
        System.out.println("Верхняя цена: " + matrix.higherCost());
        System.out.println("\nИтоговая матрица:\n" + matrix);

        System.out.println("o-------------------------------------------------o");
        //TODO Задача 2.
        //считываем данные
        Matrix matrix1 = new Matrix(new double[][]{{-1, 1, 1},
                                                {2, -2, 2},
                                                {3, 3, -3}});
        System.out.println("\nЗадание 2. \nНайти решения в смешанных стратегиях.\n\nИсходная матрица:");
        System.out.println(matrix1);
    }
}
