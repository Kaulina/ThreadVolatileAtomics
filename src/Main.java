import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;


public class Main {
    private static final AtomicInteger ATOMIC_3 = new AtomicInteger(0);
    private static final AtomicInteger ATOMIC_4 = new AtomicInteger(0);
    private static final AtomicInteger ATOMIC_5 = new AtomicInteger(0);
    private static final int NAMBER_World = 100000;

    public Main() {
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100000];

        for(int i = 0; i < texts.length; ++i) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            String[] var1 = texts;
            int var2 = texts.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String text = var1[var3];
                if (isPalindrome(text)) {
                    incrementCounter(text.length());
                }
            }

        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            String[] var1 = texts;
            int var2 = texts.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String text = var1[var3];
                if (isSameLetter(text)) {
                    incrementCounter(text.length());
                }
            }

        });
        thread2.start();
        Thread thread3 = new Thread(() -> {
            String[] var1 = texts;
            int var2 = texts.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String text = var1[var3];
                if (isAlphabeticOrder(text)) {
                    incrementCounter(text.length());
                }
            }

        });
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        PrintStream var10000 = System.out;
        int var10001 = ATOMIC_3.get();
        var10000.printf("Красивых слов с длиной 3: " + var10001 + " штук\nКрасивых слов с длиной 4: " + ATOMIC_4.get() + " штук\nКрасивых слов с длиной 5: " + ATOMIC_5.get() + " штук");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();

        for(int i = 0; i < length; ++i) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }

        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        return IntStream.range(0, text.length() / 2).noneMatch((i) -> {
            return text.charAt(i) != text.charAt(text.length() - i - 1);
        });
    }

    public static boolean isSameLetter(String text) {
        for(int i = 1; i < text.length(); ++i) {
            if (text.charAt(i) != text.charAt(i - 1)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAlphabeticOrder(String s) {
        int n = s.length();
        char[] c = new char[n];

        int i;
        for(i = 0; i < n; ++i) {
            c[i] = s.charAt(i);
        }

        Arrays.sort(c);

        for(i = 0; i < n; ++i) {
            if (c[i] != s.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    public static void incrementCounter(int text) {
        switch (text) {
            case 3 -> ATOMIC_3.incrementAndGet();
            case 4 -> ATOMIC_4.incrementAndGet();
            case 5 -> ATOMIC_5.incrementAndGet();
        }

    }
}