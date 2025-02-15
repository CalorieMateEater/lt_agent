# JavaAgent デモプロジェクト

このプロジェクトは、JavaAgent を使用して `HogeApplication` の `print()` の変数を書き換えるサンプルです。
---


## 実行環境

- **OS:** Windows 10 
- **Java:**  
java 17.0.8 2023-07-18 LTS Java(TM) SE Runtime Environment (build 17.0.8+9-LTS-211) Java HotSpot(TM) 64-Bit Server VM (build 17.0.8+9-LTS-211, mixed mode, sharing)

- **依存ライブラリ:**  
- `Javassist 3.29.2-GA`

## コマンドメモ

- 事前準備

```
Javassist 3.29.2-GA.jarを公式HPからダウンロードして、lib以下に配置

```

- ビルド（コンパイル & JAR 作成）**

```sh
rm -r bin
mkdir bin
javac -encoding UTF-8 -d bin src\ltproject_agent\HogeApplication.java
jar cmf src\ltproject_agent\MANIFEST.MF hogeapp.jar -C bin ltproject_agent/HogeApplication.class

rm -r bin
mkdir bin
javac -encoding UTF-8 -cp "lib/javassist-3.29.2-GA.jar" -d bin src\ltproject_agent\agent\*.java
cd bin
jar xf ../lib/javassist-3.29.2-GA.jar
cd ..
jar cmf src\ltproject_agent\agent\MANIFEST.MF myagent.jar -C bin .

```

- テスト実行（JavaAgent未使用)

```sh
java -jar hogeapp.jar
```

- テスト実行（JavaAgent使用)

```sh
java -javaagent:myagent.jar -jar hogeapp.jar
```


