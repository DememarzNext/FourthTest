package org.apache.commons.io;

public class AllTests {
    public static void main(String[] args) {
        testGetAdsCriticalOffset();
        testGetExtension();
        testGetName();
    }

    private static void testGetAdsCriticalOffset() {
        // 修正路径分隔符转义
        String fileName = "folder/file\\name.txt"; // Java 中表示 folder/file\name.txt
        int offset = FilenameUtils.getAdsCriticalOffset(fileName);
        System.out.println("[getAdsCriticalOffset] 文件: " + fileName);
        System.out.println("预期正确偏移: 11 | 错误偏移: " + offset); // 错误偏移应为 7
    }

    private static void testGetExtension() {
        // 添加边界测试
        String[] files = {"file.txt", "file.", "noext", "archive.tar.gz"};
        for (String file : files) {
            String ext = FilenameUtils.getExtension(file);
            System.out.println("[getExtension] 文件: " + file);
            System.out.println("预期扩展: " + getExpectedExt(file) + " | 错误扩展: " + ext);
        }
    }

    private static String getExpectedExt(String file) {
        int idx = file.lastIndexOf('.');
        return (idx == -1 || idx == file.length() - 1) ? "" : file.substring(idx + 1);
    }

    private static void testGetName() {
        // 测试无分隔符和越界情况
        String[] files = {"dir/subdir/file.txt", "file.txt", "nopath"};
        for (String file : files) {
            try {
                String name = FilenameUtils.getName(file);
                System.out.println("[getName] 文件: " + file);
                System.out.println("预期名称: " + getExpectedName(file) + " | 错误名称: " + name);
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("[getName] 文件: " + file + " | 抛出异常: " + e.getMessage());
            }
        }
    }

    private static String getExpectedName(String file) {
        int idx = Math.max(file.lastIndexOf('/'), file.lastIndexOf('\\'));
        return idx == -1 ? file : file.substring(idx + 1);
    }
}
