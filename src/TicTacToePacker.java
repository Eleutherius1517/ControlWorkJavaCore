import java.io.FileOutputStream;
import java.io.IOException;

public class TicTacToePacker {

    public static void packAndWrite(int[] grid, String filename) throws IOException {
        if (grid.length != 9) {
            throw new IllegalArgumentException("Массив должен содержать 9 элементов.");
        }

        int buffer = 0;
        for (int num : grid) {
            if (num < 0 || num > 3) {
                throw new IllegalArgumentException("Элементы должны быть в диапазоне [0, 3].");
            }
            buffer = (buffer << 2) | num; // Добавляем 2 бита в буфер
        }

        // Сдвигаем на 6 бит влево, чтобы занять 24 бита (3 байта)
        buffer <<= 6;

        // Извлекаем 3 байта
        byte[] bytes = new byte[3];
        bytes[0] = (byte) ((buffer >> 16) & 0xFF); // Старший байт
        bytes[1] = (byte) ((buffer >> 8) & 0xFF);  // Средний байт
        bytes[2] = (byte) (buffer & 0xFF);         // Младший байт

        // Запись в файл
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(bytes);
        }
    }

    public static void main(String[] args) {
        int[] grid = {0, 1, 2, 3, 0, 1, 2, 3, 0}; // Пример данных
        try {
            packAndWrite(grid, "tic_tac_toe.bin");
            System.out.println("Данные успешно записаны в файл.");
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка данных: " + e.getMessage());
        }
    }
}
