import java.io.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class client {
	static Socket sock;
	static DataOutputStream data_out;
    //static DataInputStream data_in;
    public static void main(String args[]) throws Exception {
    	//initializing socket, input and output streams, robot
	    Scanner scan = new Scanner(System.in) ; 
	    String serverName = scan.nextLine();
        int port = 554;
        sock = new Socket(serverName,port);
        System.out.println(sock);
        data_out = new DataOutputStream(sock.getOutputStream());
        //data_in = new DataInputStream(sock.getInputStream());
        //
        //Communication of msg between server and client

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));  //taking input from user 
        String out_msg = "";
        while (true) {
            out_msg = input.readLine();
            data_out.writeUTF(out_msg); //output
            data_out.flush();
            if (out_msg.equals("mouse")) {
            	while(true){
                    out_msg = input.readLine();
                    data_out.writeUTF(out_msg); //output
                    data_out.flush();
                    if (out_msg == "exit") break;
                }
            }
            else if (out_msg.equals("keyboard")) {
    	        while(true){
    	        	out_msg = input.readLine();
    	            data_out.writeUTF(out_msg); //output
    	            data_out.flush();
                    if (out_msg == "exit") break;
    	        }
    	    }
    	    else if(out_msg.equals("touch")){
    	    	JFrame f = new JFrame();
                JPanel p = new JPanel();
                JLabel imgLabel = new JLabel();
                BufferedImage img;
                int Arrlength;
                byte[] msg;
                f.setSize(1000, 750);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                p.setSize(1000,750);
                
                imgLabel.setBounds(10,10,400,400);
                
                //
                DataInputStream data_in = new DataInputStream(sock.getInputStream());
                Arrlength = data_in.readInt(); //input
                msg = new byte[Arrlength];
                data_in.readFully(msg,0,Arrlength);
                byte[] prev = new byte[Arrlength];
                //
                ByteArrayInputStream stream = new ByteArrayInputStream(msg);
                img = ImageIO.read(stream);
                
                File output = new File("D://im1.jpg");
                ImageIO.write(img,"jpg",output);
                
                imgLabel = new JLabel(new ImageIcon(img));
                p.add(imgLabel);
                f.add(p);
                //f.setVisible(true);
                //System.out.println(Arrays.equals(msg,prev_msg));
                while(true){ 
                    for(int i = 0;i<Arrlength;i++){
                        prev[i] = msg[i];
                    }
                    data_in.readFully(msg,0,Arrlength); //input
                    System.out.println(Arrays.equals(msg,prev));
                    stream = new ByteArrayInputStream(msg);
                    img = ImageIO.read(stream);
                    
                    File output1 = new File("D://im2.jpg");
                    ImageIO.write(img,"jpg",output1);
                    //imgLabel = new JLabel(new ImageIcon(img));
                    imgLabel.setVisible(true);
                    //
                    f.remove(p);
                    p = new JPanel();
                    p.add(imgLabel);
                    f.add(p);
                    f.setVisible(true);
                    //
                    Thread.sleep(1000);
                }
    	    }
            else if(out_msg.equals("screenshot")){
                DataInputStream data_in = new DataInputStream(sock.getInputStream());
                JFrame f = new JFrame();
                JPanel p = new JPanel();
                ByteArrayInputStream stream;
                BufferedImage img ;
                int Arrlength;
                byte[] msg;
                f.setSize(1000, 750);
                p.setSize(1000,750);
                JLabel imgLabel = new JLabel();
                imgLabel.setBounds(10,10,400,400);
                imgLabel.setVisible(true);
                //
                Arrlength = data_in.readInt(); //input
                msg = new byte[Arrlength];
                data_in.readFully(msg,0,Arrlength); //input
                stream = new ByteArrayInputStream(msg);
                img = ImageIO.read(stream);
                imgLabel = new JLabel(new ImageIcon(img));
                p.add(imgLabel);
                f.add(new JScrollPane(p));
                f.setVisible(true);
            }
            else if (out_msg.equals("filetransfer")){
                //FileOutputStream file_out = new FileOutputStream(sock.getOutputStream());
                byte[] data;
                int fileSize;
                String filename; 

                DataInputStream data_in = new DataInputStream(sock.getInputStream());

                fileSize = data_in.readInt();
                filename = data_in.readUTF();
                data = new byte[fileSize];
                data_in.readFully(data,0,fileSize);
                
                String path = "D://";
                path = path+filename;
                File outfile = new File(path);
                FileOutputStream stream = new FileOutputStream(outfile);
                stream.write(data,0,fileSize);
                System.out.println("file recieved");
            }
            else if (out_msg.equals("filerecieve")){
                DataOutputStream data_out = new DataOutputStream(sock.getOutputStream());
                        JFileChooser chooser = new JFileChooser();
                        File myfile;
                        String filename;
                        int fileSize ;
                        FileInputStream in_stream;
                        byte[] data ;
                        int i = chooser.showOpenDialog(null);
                        if(i == JFileChooser.APPROVE_OPTION) {
                            myfile = chooser.getSelectedFile();
                            in_stream = new FileInputStream(myfile);
                            fileSize = (int) myfile.length();
                            filename = myfile.getName();
                            data = new byte[fileSize];
                            in_stream.read(data,0,fileSize);
                            data_out.writeInt(fileSize);
                            data_out.writeUTF(filename);
                            data_out.write(data);
                            System.out.println("file sent");
                        }
            }
            else if (out_msg == "exit1") break;
        }
    }
}