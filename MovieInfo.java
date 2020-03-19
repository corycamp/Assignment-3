import java.util.*;
import java.io.*;
//          Cory Campbell
//          CISC 3130
//          Assignment #3
//          MW 9:05 - 10:45

/*
    Uses a main method which initializes each line from the csv file to an array
    sends that array to a method called setKey in the MovieBST class which separates, trim and formats values from the line to be stored as values in the Movie Node
    With a while loop, each formatted line is sent to the Movie class which creates a new Movie node containing the information
    Those Nodes are sent to a method named insert, which creates the tree using these nodes
    Another method is created call subset which is used to traverse the tree for values in between the specified values
 */

public class MovieInfo {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new FileReader("D:\\Assignment#3\\movies\\movies.csv"));
        final int amount = 21;
        String arr[] = new String[amount];
        int count = 0;
        //Initializes the array with lines from the file
        while (sc.hasNext() && count < amount) {
            arr[count] = sc.nextLine();
            count++;
        }
        //Creates the Binary search tree object and sends it the array
        MoviesBST p = new MoviesBST();
        p.setKey(arr);

    }
}
//Movie method for creating nodes
    class Movie {
        private String title;
        private int year;
        private String[] genre;
        private int movieID;
        private Movie left;
        private Movie right;

        public Movie() {
            title = null;
            right = null;
            left = null;
        }
        public void setKey(String title){
            this.title = title;
        }
        public void add(String title, int year, String[] genre, int movieID) {
            this.title = title;
            this.year = year;
            this.genre = genre;
            this.movieID = movieID;
        }
        public void display() {
            if (this == null) {
                System.out.println("Empty");
            } else {
                System.out.print(title + " " + year);
                for (String x : genre)
                    System.out.print(" " + x);
                System.out.print(" " + movieID + "\n");
            }
        }
        //Assigns values to node and retrieves values from node
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return title;
        }
        public void setRight(Movie node){
            right = node;
        }
        public void setLeft(Movie node){
            left = node;
        }
        public Movie getLeft(){
            return left;
        }
        public Movie getRight(){
            return right;
        }
    }
//Creates the Binary tree from Array sent from main
    class MoviesBST {
        private Movie root;
        public int num = 0;

        public MoviesBST(){
            root = null;
        }
        //While loop cleans up each line to make assigning values to nodes easier
        public void setKey(String[] arr) throws Exception {
            Movie temp = new Movie();
            Movie tree = new Movie();
            int count = 1;
            while ((!(arr[count] == null)) && count <= 19) {
                String[] x = arr[count].split(",");
                String piece1 = null;
                for (int i = 0; i < x.length; i++) {
                    for (int j = 0; j < x[i].length(); j++) {
                        if (x[i].charAt(j) == '"' && x[i + 1].charAt(x[i + 1].length() - 1) == '"') {
                            piece1 = x[i] + x[i + 1];
                            x[2] = x[3];
                        }
                    }
                }

                //The movieID of each movie in a String format
                String num = x[0];
                //An array of the genre of each movie
                String[] genre = x[2].split("\\|");

                String name = x[1].replaceAll("[^0-9]", "");
                String name2 = null;
                if (!(piece1 == null))
                    name2 = piece1.replaceAll("[^0-9]", "");

                //Assigns the numeric value of the movieID by parsing
                int movieID = Integer.parseInt(num);
                int year = 0;
                String title = null;

                //Assigns movie title to a variable
                if (piece1 == null) {
                    title = x[1].replaceAll("[0-9]", "");
                    title = title.replaceAll("[\\\\[\\\\](){}]", "");
                    title.trim();
                } else if (!(piece1 == null)) {
                    title = piece1;
                    title = title.replaceAll("[0-9]", "");
                    title = title.replaceAll("[\\\\[\\\\](){}]", "");
                    title = title.replaceAll("^\"|\"$", "");
                    title.trim();
                }

                //Years of each movies release
                if (piece1 == null) {
                    year = Integer.parseInt(name);
                } else if (!(piece1 == null)) {
                    year = Integer.parseInt(name2);
                }

                temp.add(title, year, genre, movieID);

                tree = insert(temp,title);
                count++;
            }
        }
//Creates the Binary tree
        public static Movie insert(Movie root, String key) {
            // Create a new Node containing
            Movie newnode = new Movie();
            newnode.setKey(key);

            // Pointer to start traversing from root and
            // traverses downward path to search
            // where the new node to be inserted
            Movie x = root;

            // Pointer y maintains the trailing
            // pointer of x
            Movie y = null;

            while (x != null) {
                y = x;
                if (key.compareTo(x.getTitle()) < 0)
                    x = x.getLeft();
                else
                    x = x.getRight();
            }

            if (y == null)
                y = newnode;
                // Assign the new node to be its left child
            else if (key.compareTo(y.getTitle()) < 0)
                y.setLeft(newnode);

                // else assign the new node its right child
            else
                y.setRight(newnode);

            return y;
        }

//Traverses the tree created from the Movie Nodes
        public static void Inorder (Movie root){
            if (root == null)
                return;
            else {
                Inorder(root.getLeft());
                System.out.println(root.getTitle() + " ");
                Inorder(root.getRight());
            }
        }
//Searches for names between the specific names provided
        public void subset(String start, String end) {
            Movie node = root;

            if (node == null) {
                return;
            }

            if (start.compareTo(node.getTitle()) < 0) {
                node = node.getLeft();
                subset(start, end);
            }

            if (start.compareTo(node.getTitle()) <= 0 && end.compareTo(node.getTitle()) >= 0) {
                System.out.print(node.getTitle() + " ");
            }

            if (end.compareTo(node.getTitle()) > 0) {
                node = node.getRight();
                subset(start, end);
            }
        }
    }



