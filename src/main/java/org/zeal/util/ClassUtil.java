package org.zeal.util;

/**
 * Created by wangjw on 2015/10/10.
 */
public class ClassUtil {
    /**
     * @param c
     * @return
     * @Description: 返回类class文件所在的目录
     */
    public static String getClassPath(Class<?> c) {
        return c.getResource("").getPath().replaceAll("%20", " ");
    }

    /**
     * @param c
     * @param hasName 是否显示文件名
     * @return 返回类class文件的地址
     * @Description:
     */
    public static String getClassPath(Class<?> c, boolean hasName) {
        String name = c.getSimpleName() + ".class";
        String path = c.getResource(name).getPath().replaceAll("%20", " ");
        if (hasName) {
            return path;
        } else {
            return path.substring(0, path.length() - name.length());
        }
    }

    /**
     * @param c
     * @return
     * @Description: 返回类class文件所在的顶级目录
     */
    public static String getClassRootPath(Class<?> c) {
        return c.getResource("/").getPath().replaceAll("%20", " ");
    }

    public static void main(String[] args) {
        System.out.println(ClassUtil.getClassPath(ClassUtil.class, true));
        System.out.println(ClassUtil.getClassPath(Math.class, true));
        System.out.println(ClassUtil.getClassRootPath(Math.class));
    }
}
