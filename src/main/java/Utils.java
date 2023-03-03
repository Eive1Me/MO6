public class Utils {
    public static Double compress(Double value) {
        int valueInt = (int) Math.round(value * 1000);
        return ((double) valueInt) / 1000;
    }

    public static boolean isInteger(String s) {
        String digits = "0123456789";
        for (int i = 0; i < s.length(); ++i) {
            if (!digits.contains(String.valueOf(s.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static void inputFirstTaskData(MatrixFractional matrixFractional){
        matrixFractional.set(new Cell("x1", "y1"), +2.0);
        matrixFractional.set(new Cell("x2", "y1"), +0.0);
        matrixFractional.set(new Cell("x3", "y1"), +1.0);
        matrixFractional.set(new Cell("x4", "y1"), +4.0);

        matrixFractional.set(new Cell("x1", "y2"), +1.0);
        matrixFractional.set(new Cell("x2", "y2"), +2.0);
        matrixFractional.set(new Cell("x3", "y2"), +5.0);
        matrixFractional.set(new Cell("x4", "y2"), +3.0);

        matrixFractional.set(new Cell("x1", "y3"), +4.0);
        matrixFractional.set(new Cell("x2", "y3"), +1.0);
        matrixFractional.set(new Cell("x3", "y3"), +3.0);
        matrixFractional.set(new Cell("x4", "y3"), +2.0);
    }

    public static void inputSecondTaskData(MatrixFractional matrixFractional) {
        matrixFractional.set(new Cell("x1", "y1"), -1.0);
        matrixFractional.set(new Cell("x2", "y1"), +1.0);
        matrixFractional.set(new Cell("x3", "y1"), +1.0);

        matrixFractional.set(new Cell("x1", "y2"), +2.0);
        matrixFractional.set(new Cell("x2", "y2"), -2.0);
        matrixFractional.set(new Cell("x3", "y2"), +2.0);

        matrixFractional.set(new Cell("x1", "y3"), +3.0);
        matrixFractional.set(new Cell("x2", "y3"), +3.0);
        matrixFractional.set(new Cell("x3", "y3"), -3.0);
    }
}