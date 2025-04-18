package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FilenameUtilsTest {

    @Test
    public void testGetAdsCriticalOffset() {
        // 修正路径分隔符转义
        String fileName = "folder/file\\name.txt"; // Java 中表示 folder/file\name.txt
        int offset = FilenameUtils.getAdsCriticalOffset(fileName);
        System.out.println("[getAdsCriticalOffset] 文件: " + fileName);
        System.out.println("预期正确偏移: 11 | 错误偏移: " + offset);
        assertEquals(11, offset, "偏移量应该为 11");
    }

    @Test
    public void testGetExtension() {
        // 添加边界测试
        String[] files = {"file.txt", "file.", "noext", "archive.tar.gz"};
        for (String file : files) {
            String ext = FilenameUtils.getExtension(file);
            System.out.println("[getExtension] 文件: " + file);
            System.out.println("预期扩展: " + getExpectedExt(file) + " | 错误扩展: " + ext);
            assertEquals(getExpectedExt(file), ext, "扩展名应该是 " + getExpectedExt(file));
        }
    }

    private String getExpectedExt(String file) {
        int idx = file.lastIndexOf('.');
        return (idx == -1 || idx == file.length() - 1) ? "" : file.substring(idx + 1);
    }

    @Test
    public void testGetName() {
        // 测试无分隔符和越界情况
        String[] files = {"dir/subdir/file.txt", "file.txt", "nopath"};
        for (String file : files) {
            try {
                String name = FilenameUtils.getName(file);
                System.out.println("[getName] 文件: " + file);
                System.out.println("预期名称: " + getExpectedName(file) + " | 错误名称: " + name);
                assertEquals(getExpectedName(file), name, "文件名应该是 " + getExpectedName(file));
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("[getName] 文件: " + file + " | 抛出异常: " + e.getMessage());
                fail("文件名获取时抛出了异常: " + e.getMessage());
            }
        }
    }

    private String getExpectedName(String file) {
        int idx = Math.max(file.lastIndexOf('/'), file.lastIndexOf('\\'));
        return idx == -1 ? file : file.substring(idx + 1);
    }
}
