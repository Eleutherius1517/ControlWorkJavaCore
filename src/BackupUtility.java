import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class BackupUtility {

    public static void createBackup(String sourceDirPath) {
        File sourceDir = new File(sourceDirPath);
        File backupDir = new File(sourceDir.getParent(), "backup");

        // Проверяем, существует ли исходная директория
        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            System.out.println("Исходная директория не существует или не является директорией.");
            return;
        }

        // Создаем папку для резервной копии, если она не существует
        if (!backupDir.exists()) {
            if (backupDir.mkdir()) {
                System.out.println("Папка для резервной копии создана: " + backupDir.getAbsolutePath());
            } else {
                System.out.println("Не удалось создать папку для резервной копии.");
                return;
            }
        }

        // Получаем список файлов в исходной директории
        File[] files = sourceDir.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("Исходная директория пуста.");
            return;
        }

        // Копируем файлы в папку резервной копии
        for (File file : files) {
            if (file.isFile()) {
                try {
                    Path sourcePath = file.toPath();
                    Path backupPath = new File(backupDir, file.getName()).toPath();
                    Files.copy(sourcePath, backupPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Скопирован файл: " + file.getName());
                } catch (IOException e) {
                    System.out.println("Ошибка при копировании файла: " + file.getName());
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Резервное копирование завершено.");
    }

    public static void main(String[] args) {
        // Укажите путь к директории, для которой нужно создать резервную копию
        String sourceDirectory = "./your_source_directory";
        createBackup(sourceDirectory);
    }
}
