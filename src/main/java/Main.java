public class Main {
    public static void main(String[] args) {
        //TODO Задача 1.
        //считываем данные
        Matrix matrix = new Matrix(new int[][]{{2,0,1,4},
                                               {1,2,5,3},
                                               {4,1,3,2}});
        System.out.println("\nЗадание 1. \nНайти верхнюю и нижнюю цены игры и седловую точку.\n\nИсходная матрица:");
        System.out.println(matrix);
        System.out.println("Седловая точка: " + matrix.saddlePoint());
        System.out.println("Нижняя цена: " + matrix.lowerCost());
        System.out.println("Верхняя цена: " + matrix.higherCost());
        System.out.println("\nИтоговая матрица:\n" + matrix);

        //TODO Задача 2.
        //считываем данные
    }
}
