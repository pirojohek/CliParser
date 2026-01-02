# 📦 Инструкция по установке и запуску

## Системные требования

### Обязательные компоненты для запуска

| Компонент | Минимальная версия | Рекомендуемая версия | Ссылка для скачивания |
|-----------|-------------------|---------------------|----------------------|
| **Java JDK** | 17 | 17+ | https://adoptium.net/ |

### Дополнительные компоненты для сборки из исходников

| Компонент | Минимальная версия | Рекомендуемая версия | Ссылка для скачивания |
|-----------|-------------------|---------------------|----------------------|
| **Apache Maven** | 3.6.0 | 3.9.0+ | https://maven.apache.org/download.cgi |
| **Git** | Любая | Последняя | https://git-scm.com/downloads |

---

## Способ 1: Запуск готового JAR файла (рекомендуется)

### Шаг 1: Проверка установки Java

```bash
java -version
```

**Ожидаемый вывод:**
```
openjdk version "17.0.X" 2023-XX-XX
OpenJDK Runtime Environment (build 17.0.X+X)
OpenJDK 64-Bit Server VM (build 17.0.X+X, mixed mode, sharing)
```

Если Java не установлена или версия < 17, установите Java 17+ с https://adoptium.net/

### Шаг 2: Скачивание готового JAR

**Опция A: Через Git**
```bash
git clone https://github.com/yourusername/CliUtilForFilterFiles.git
cd CliUtilForFilterFiles
```

**Опция B: Прямое скачивание**
Скачайте файл `app-jar-with-dependencies.jar` из релизов проекта.

### Шаг 3: Запуск

```bash
java -jar target/app-jar-with-dependencies.jar -s input.txt
```

**Если JAR находится в другой директории:**
```bash
java -jar /path/to/app-jar-with-dependencies.jar -s input.txt
```

---

## Способ 2: Сборка из исходников

### Шаг 1: Проверка установки всех компонентов

**Проверка Java:**
```bash
java -version
javac -version
```

**Проверка Maven:**
```bash
mvn -version
```

**Ожидаемый вывод Maven:**
```
Apache Maven 3.9.X (...)
Maven home: /usr/local/maven
Java version: 17.0.X, vendor: Eclipse Adoptium
```

### Шаг 2: Клонирование репозитория

```bash
git clone https://github.com/yourusername/CliUtilForFilterFiles.git
cd CliUtilForFilterFiles
```

### Шаг 3: Сборка проекта

```bash
mvn clean package
```

**Процесс сборки включает:**
1. Очистка предыдущих артефактов (`clean`)
2. Компиляция исходного кода
3. Запуск unit тестов (можно пропустить: `mvn clean package -DskipTests`)
4. Создание JAR файлов

**Ожидаемый вывод:**
```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  X.XXX s
[INFO] Finished at: 2026-01-03T...
[INFO] ------------------------------------------------------------------------
```

**Созданные артефакты:**
- `target/app.jar` - базовый JAR без зависимостей
- `target/app-jar-with-dependencies.jar` - полный JAR со всеми зависимостями (рекомендуется)

### Шаг 4: Запуск

```bash
java -jar target/app-jar-with-dependencies.jar -s input.txt
```

---

## Проверка установки

### Тест 1: Проверка версии и справки

```bash
java -jar target/app-jar-with-dependencies.jar
```

**Ожидаемый вывод:**
```
Ошибка аргументов: Не указан тип статистики...

Использование:
  java -jar app.jar [опции] <входные_файлы>

Опции:
  -o <путь>      : директория для выходных файлов
  ...
```

### Тест 2: Простая обработка

**Создание тестового файла:**
```bash
echo -e "123\nhello\n3.14" > test.txt
```

**Запуск:**
```bash
java -jar target/app-jar-with-dependencies.jar -s test.txt
```

**Ожидаемый вывод:**
```
Найдено файлов для обработки: 1
=== Статистика ===

Числовая статистика:
  Количество: 2

Строковая статистика:
  Количество: 1

✓ Обработка успешно завершена
```

**Проверка созданных файлов:**
```bash
ls *.txt
# Должно быть: test.txt integers.txt floats.txt strings.txt
```

---

## Устранение неполадок

### Проблема: "java: command not found"

**Причина:** Java не установлена или не добавлена в PATH.

**Решение:**
1. Установите Java 17+ с https://adoptium.net/
2. Добавьте Java в PATH:
   ```bash
   # Linux/macOS (.bashrc или .zshrc)
   export JAVA_HOME=/path/to/java
   export PATH=$JAVA_HOME/bin:$PATH
   
   # Windows (Переменные среды)
   JAVA_HOME=C:\Program Files\Java\jdk-17
   PATH=%JAVA_HOME%\bin;%PATH%
   ```

### Проблема: "Unsupported class file major version 61"

**Причина:** Установлена Java версии < 17.

**Решение:**
Обновите Java до версии 17 или выше:
```bash
java -version  # Проверьте текущую версию
# Скачайте Java 17+ с https://adoptium.net/
```

### Проблема: "mvn: command not found"

**Причина:** Maven не установлен.

**Решение:**

**Linux:**
```bash
sudo apt-get install maven  # Ubuntu/Debian
sudo yum install maven      # CentOS/RHEL
```

**macOS:**
```bash
brew install maven
```

**Windows:**
1. Скачайте Maven с https://maven.apache.org/download.cgi
2. Распакуйте в `C:\Program Files\Maven`
3. Добавьте в PATH: `C:\Program Files\Maven\bin`

### Проблема: "NoClassDefFoundError: com/fasterxml/jackson/..."

**Причина:** Используется неправильный JAR файл (без зависимостей).

**Решение:**
Используйте `app-jar-with-dependencies.jar` вместо `app.jar`:
```bash
java -jar target/app-jar-with-dependencies.jar -s input.txt
```

### Проблема: Тесты падают при сборке

**Причина:** Проблемы с тестовым окружением.

**Временное решение:**
Пропустите тесты при сборке:
```bash
mvn clean package -DskipTests
```

**Постоянное решение:**
Проверьте логи тестов в `target/surefire-reports/` и исправьте проблемы.

---

## Дополнительные возможности

### Создание исполняемого скрипта (Linux/macOS)

Создайте файл `filter`:
```bash
#!/bin/bash
java -jar /path/to/app-jar-with-dependencies.jar "$@"
```

Сделайте исполняемым:
```bash
chmod +x filter
sudo mv filter /usr/local/bin/
```

Использование:
```bash
filter -s input.txt
```

### Создание bat файла (Windows)

Создайте файл `filter.bat`:
```batch
@echo off
java -jar C:\path\to\app-jar-with-dependencies.jar %*
```

Добавьте директорию с `filter.bat` в PATH.

Использование:
```cmd
filter -s input.txt
```

### Запуск с увеличенной памятью

Для обработки очень больших файлов:
```bash
java -Xmx4g -jar target/app-jar-with-dependencies.jar -s huge_file.txt
```

- `-Xmx4g` - максимум 4 ГБ heap памяти
- Можно указать больше, если доступно (8g, 16g и т.д.)

---

## Версии зависимостей

### Production зависимости

```xml
<!-- Jackson для JSON сериализации -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.19.2</version>
</dependency>
```

**Документация:** https://github.com/FasterXML/jackson-databind  
**Maven Central:** https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind

### Test зависимости

```xml
<!-- JUnit 5 -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>

<!-- Mockito -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.11.0</version>
    <scope>test</scope>
</dependency>

<!-- AssertJ -->
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.25.3</version>
    <scope>test</scope>
</dependency>
```

---

## Проверка целостности JAR

После сборки можно проверить содержимое JAR:

```bash
jar tf target/app-jar-with-dependencies.jar | head -20
```

**Ожидаемый вывод:**
```
META-INF/
META-INF/MANIFEST.MF
by/pirog/App.class
by/pirog/ApplicationContext.class
by/pirog/ApplicationRunner.class
...
com/fasterxml/jackson/...
```

Проверка главного класса:
```bash
jar xf target/app-jar-with-dependencies.jar META-INF/MANIFEST.MF
cat META-INF/MANIFEST.MF
```

**Должно содержать:**
```
Main-Class: by.pirog.App
```

---

## Следующие шаги

После успешной установки см.:
- **[Быстрый старт](QUICKSTART.md)** - примеры использования
- **[README](README.md)** - полная документация
- **[Особенности](HIGHLIGHTS.md)** - ключевые преимущества

---

**Версия документа:** 1.0  
**Дата:** 03.01.2026  
**Автор:** Кирилл Замиралов

