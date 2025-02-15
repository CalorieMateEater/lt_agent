package ltproject_agent.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import javassist.*;

public class ClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, 
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) 
                            throws IllegalClassFormatException {
        
        String fixedClassName = className.replace("/", ".");
        if (!fixedClassName.equals("ltproject_agent.HogeApplication")) {
            return classfileBuffer;
        }

        try {
            // クラスプールを作成し、クラスをロード
            ClassPool pool = new ClassPool(true);
            if (loader != null) {
                pool.insertClassPath(new LoaderClassPath(loader));
            }

            CtClass ctClass = pool.get(fixedClassName);
            CtMethod printMethod = ctClass.getDeclaredMethod("print");

            // `print` メソッド内で `x` の値を `3` に書き換える
            printMethod.insertBefore("{ $1 = 3; }");

            // 変更を適用してバイトコードを返す
            byte[] byteCode = ctClass.toBytecode();
            ctClass.detach();
            return byteCode;
        } catch (Exception e) {
            System.err.println("[JavaAgent] Failed to modify print method!");
            e.printStackTrace();
        }
        return classfileBuffer;
    }
}
