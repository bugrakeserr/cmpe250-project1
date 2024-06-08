import java.io.*;

public class Main {
    public static void main(String[] args) {
        AvlTree t = new AvlTree();
        String inputFile;
        String outputFile;
        try{
            inputFile = args[0];
            outputFile = args[1];
        } catch (Exception e) {
            inputFile = "input.txt";
            outputFile = "output.txt";
        }
        try {
            FileReader file = new FileReader(inputFile);
            BufferedReader bufferedReader = new BufferedReader(file);
            String firstLine = bufferedReader.readLine();
            String[] bossData = firstLine.split(" ");
            FileWriter writer = new FileWriter(outputFile);
            BufferedWriter myWriter = new BufferedWriter(writer);
            Node boss = new Node(bossData[0], Float.parseFloat(bossData[1]));

            // Insert the first element to the family
            t.insert(boss, myWriter);
            while (bufferedReader.ready()) {
                String data = bufferedReader.readLine();
                String[] dataArr = data.split(" ");
                String word = dataArr[0];
                if(word.equals("MEMBER_IN")) memberIn(t, dataArr, myWriter);
                else if(word.equals("MEMBER_OUT")) memberOut(t, dataArr, myWriter);
                else if(word.equals("INTEL_TARGET")) intelTarget(t, dataArr, myWriter);
                else if(word.equals("INTEL_RANK")) intelRank(t, dataArr, myWriter);
                else if(word.equals("INTEL_DIVIDE")) divide(t, myWriter);
            }
            myWriter.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Insert new member to the family.
     * @param familyTree the family to be inserted in.
     * @param data the related information array.
     * @param myWriter the file writer.
     * @throws IOException
     */
    public static void memberIn(AvlTree familyTree, String[] data, BufferedWriter myWriter) throws IOException {
            String name = data[1];
            float gms = Float.parseFloat(data[2]);
            Node member = new Node(name, gms);
            familyTree.insert(member, myWriter);
    }

    /**
     * Removes the member from the family.
     * @param familyTree the family to be removed out.
     * @param data the related information array.
     * @param myWriter the file writer.
     * @throws IOException
     */
    public static void memberOut(AvlTree familyTree, String[] data, BufferedWriter myWriter) throws IOException {
        String name = data[1];
        float gms = Float.parseFloat(data[2]);
        Node x = new Node(name, gms);
        myWriter.write(name + " left the family, replaced by " + familyTree.leave(x) + "\n");
        familyTree.remove(x);
    }

    /**
     * Targets the common least superior element of the given both elements.
     * @param familyTree the family to target.
     * @param data the related information array.
     * @param myWriter file writer.
     * @throws IOException
     */
    public static void intelTarget(AvlTree familyTree, String[] data, BufferedWriter myWriter) throws IOException {
        String nameX = data[1];
        float gmsX = Float.parseFloat(data[2]);
        Node memberX = new Node(nameX, gmsX);
        String nameY = data[3];
        float gmsY = Float.parseFloat(data[4]);
        Node memberY = new Node(nameY, gmsY);
        Node target = familyTree.target(memberX, memberY);
        myWriter.write("Target Analysis Result: " + target.getName() + " " + String.format("%.3f", target.getGms()) + "\n");
    }

    /**
     * Gives information who have the same rank in the family.
     * @param familyTree the family that is being investigated.
     * @param data the related information array.
     * @param myWriter the file writer.
     * @throws IOException
     */
    public static void intelRank(AvlTree familyTree, String[] data, BufferedWriter myWriter) throws IOException {
        String name = data[1];
        float gms = Float.parseFloat(data[2]);
        Node x = new Node(name, gms);
        myWriter.write("Rank Analysis Result:");
        familyTree.sameRank(x, myWriter);
        myWriter.write("\n");
    }

    /**
     * Finds the maximum number of independent(not superior or inferior to each other) elements in the family.
     * @param familyTree the family to search in.
     * @param myWriter the file writer.
     * @throws IOException
     */
    public static void divide(AvlTree familyTree, BufferedWriter myWriter) throws IOException {
        int result = familyTree.findMaxIndependentNodes();
        myWriter.write("Division Analysis Result: " + result + "\n");
    }
}
