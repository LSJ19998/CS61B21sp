package capers;

import java.io.File;
import java.io.Serializable;

import static capers.Utils.*;

/** A repository for Capers 
 * @author TODO
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */


public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    // you can use join function, and you can use File function
    static final File CAPERS_FOLDER = new File(".capers"); // TODO Hint: look at the `join`
                                            //      function in Utils

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        CAPERS_FOLDER.mkdir();
        Dog.DOG_FOLDER.mkdir();
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        // TODO

        // 建立对于story文件的引用
        File story = join(CAPERS_FOLDER, "story.txt");

        // 这里有两种情况:  1. story文件不存在, 不用修改字符串 + 操作文件前, 会构造一个文件
        // 2. story文件存在, 字符串为原本文件内的字符串 和 新加入的字符串
        if (story.exists()) {
            text = readContentsAsString(story) + "\n" + text;
        }

        // 将字符串写入文件内
        writeContents(story, text);

        // 打印字符串
        System.out.println(text);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        // TODO
        // 构造Dog实例
        Dog newDog = new Dog(name, breed, age);
        // Dog实例存入DogName文件中
        newDog.saveDog();
        // 打印Dog实例
        System.out.println(newDog);
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        // TODO

        // 从文件中提取出Dog实例
        Dog dogFromFile = Dog.fromFile(name);

        // 改变Dog实例 -> age + 1
        dogFromFile.haveBirthday();

        // 再将Dog实例存入文件中
        dogFromFile.saveDog();
    }
}
