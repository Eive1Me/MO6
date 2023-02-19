import java.util.Arrays;
import java.util.List;

public class Matrix {
    double[][] matrix;

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("");
        // Loop through all rows
        for (double[] row : matrix)
            // converting each row as string
            // and then printing in a separate line
            stringBuilder.append(Arrays.toString(row)).append('\n');
        return stringBuilder.toString();
    }

    public Node lowerCost(){
        try {
            double[][] addedColumn = new double[matrix.length][1];
            for (int i = 0; i < matrix.length; i++) {
                addedColumn[i][0] = matrix[i][0];
            }
            this.addRight(addedColumn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length - 1; j++) {
                if (matrix[i][j] < matrix[i][matrix[0].length - 1]) {
                    matrix[i][matrix[0].length - 1] = matrix[i][j];
                }
            }
        }
        Node result = new Node(0, matrix[0].length - 1);
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][matrix[0].length - 1] > matrix[result.a][result.b]){
                result = new Node(i,matrix[0].length - 1);
            }
        }
        return result;
    }

    public Node higherCost(){
        try {
            double[][] addedRow = new double[1][matrix[0].length];
            for (int i = 0; i < matrix[0].length - 1; i++) {
                addedRow[0][i] = matrix[0][1];
            }
            addedRow[0][matrix[0].length - 1] = 0;
            this.addDown(addedRow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length - 1; j++) {
                if (matrix[i][j] > matrix[matrix.length - 1][j]) {
                    matrix[matrix.length - 1][j] = matrix[i][j];
                }
            }
        }
        Node result = new Node(matrix.length - 1, 0);
        for (int i = 0; i < matrix[0].length - 1; i++) {
            if (matrix[matrix.length - 1][i] < matrix[result.a][result.b]){
                result = new Node(matrix.length - 1, i);
            }
        }
        return result;
    }

    public void addRight(double[][] mas) throws Exception {
        if (mas.length == matrix.length){
            double[][] newMatrix = new double[matrix.length][matrix[0].length + 1];
            // Merge the two matrices
            for(int i = 0; i < matrix.length; i++) {
                for(int j = 0; j < matrix[0].length; j++) {
                    // To store elements
                    // of matrix A
                    newMatrix[i][j] = matrix[i][j];
                }
                for(int j = 0; j < mas[0].length; j++) {
                    // To store elements
                    // of matrix B
                    newMatrix[i][j + matrix[0].length] = mas[i][j];
                }
            }
            matrix = newMatrix;
        } else {
            throw new Exception("Mass length and number of rows don't match");
        }
    }

    public void addDown(double[][] mas) throws Exception {
        if (mas[0].length == matrix[0].length) {
            double[][] newMatrix = new double[matrix.length + mas.length][matrix[0].length];
            System.arraycopy(matrix, 0, newMatrix, 0, matrix.length);
            System.arraycopy(mas, 0, newMatrix, matrix.length, mas.length);
            matrix = newMatrix;
        }else {
            throw new Exception("Mass length and number of rows don't match");
        }
    }

    public Node saddlePoint(){
        Node result = new Node(-1,-1);
        for (int i = 0; i < matrix.length; i++) {
            boolean isSaddlePointExist = true;
            double minimum = matrix[i][0];
            int colIndexOfRowMinimum = 0;

            //finds minimum in row
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] < minimum) {
                    minimum = matrix[i][j];
                    colIndexOfRowMinimum = j;
                }
            }

            //find maximum in same column
            for (int j = 0; j < matrix.length; j++) {
                if (minimum < matrix[j][colIndexOfRowMinimum]) {
                    //if the above condition becomes true set saddle point to false
                    isSaddlePointExist = false;
                    break;
                }
            }

            if (isSaddlePointExist) {
                //saves the saddle point
                //System.out.println("The saddle point of the matrix is: " + minimum);
                result = new Node(i, colIndexOfRowMinimum);
            }
        }
        return result;
    }

    public class Node{
        int a,b;

        public Node(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public boolean exists(){
            if (a < 0 || b < 0 || a > matrix.length || b > matrix[0].length) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            if (exists()) {
                return "Точка (" + a + "," + b + "), со значением " + matrix[a][b] + '.';
            } else return "Точки не существует.";
        }
    }
}
