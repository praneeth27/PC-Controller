import java.io.*;
import java.io.ByteArrayOutputStream;
import java.net.*;
import java.awt.*;
import java.awt.Robot;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.bluetooth.*;
import javax.microedition.io.*;


public class server2 implements ActionListener{
    static Robot robo;
    static ServerSocket serv_sock;
    static Socket sock;
    static DataInputStream data_in;
    static DataOutputStream data_out;
    static boolean mode;
    static InetAddress ip;
    static LocalDevice localDevice;
    static JButton bluetooth = new JButton("BLUETOOTH");
    static JButton wifi = new JButton("WIFI");
    static JLabel address = new JLabel();
    static JLabel address2 = new JLabel();
    static JFrame f = new JFrame("SERVER SIDE APPLICATION");
    static JPanel p = new JPanel();
    //
    server2(){
        bluetooth.addActionListener(this);
        wifi.addActionListener(this);
    }
    public static void keyStroke(char key) {
        if (Character.isUpperCase(key)) {
            robo.keyPress(KeyEvent.VK_SHIFT);
        }
        robo.keyPress(Character.toUpperCase(key));
        robo.delay(10);
        robo.keyRelease(Character.toUpperCase(key));
        if (Character.isUpperCase(key)) {
            robo.keyRelease(KeyEvent.VK_SHIFT);
        }
    }
    public static void wifi_remote() throws Exception{
            int port  = 554;
            serv_sock = new ServerSocket(port);
            System.out.println(ip.getHostAddress());
            //address.setVisible(true);
            JOptionPane.showMessageDialog(f.getComponent(0), ip.getHostAddress());
            //p.revalidate();
            sock = serv_sock.accept();
            System.out.println("CLIENT CONNECTED");
            robo = new Robot(); 
            data_out = new DataOutputStream(sock.getOutputStream());
            data_in = new DataInputStream(sock.getInputStream());
            process();
    }
    public static void bluetooth_remote() throws Exception{
    	
    	localDevice.setDiscoverable(DiscoveryAgent.GIAC);
        System.out.println("Address: "+localDevice.getBluetoothAddress());
        System.out.println("Name: "+localDevice.getFriendlyName());
        address2.setVisible(true);
        JOptionPane.showMessageDialog(f.getComponent(0), localDevice.getBluetoothAddress());
        p.revalidate();
    	//UUID uuid = new UUID("1101",true);
        UUID uuid = new UUID ("0000110100001000800000805F9B34FB",false);
    	String connectionString = "btspp://localhost:" + uuid +";name=SPPServer";
        StreamConnectionNotifier streamConnNotifier = (StreamConnectionNotifier)Connector.open(connectionString);
        
        //Wait for client connection
        StreamConnection connection=streamConnNotifier.acceptAndOpen();
        System.out.println("Client Connected");
        
        data_in = new DataInputStream(connection.openInputStream());
        data_out = new DataOutputStream(connection.openOutputStream());
        streamConnNotifier.close();
    	robo = new Robot();
    	
    	process();
    }
    public static void process() throws Exception {
                String in_msg = data_in.readUTF();//first input
                System.out.println(in_msg);
                while (! in_msg.equals("exit1")){

                    if(in_msg.equals("mouse")) {
                        //System.out.println("reached mouse");
                        float diffx,diffy;
                        in_msg = data_in.readUTF();//input
                        //System.out.println(in_msg);
                        while (! in_msg.equals("exit")){
                            if(in_msg.equals("left_click")){
                                robo.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                robo.delay(10);
                                robo.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            }
                            else if(in_msg.equals("right_click")){
                                robo.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                                robo.delay(10);
                                robo.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                            }
                            else if(in_msg.equals("move")){
                                Point point = MouseInfo.getPointerInfo().getLocation();
                                diffx = Float.parseFloat(data_in.readUTF());
                                diffy = Float.parseFloat(data_in.readUTF());
                                robo.mouseMove((int) (diffx/20+point.x) , (int) (diffy/20+point.y));
                            }
                            in_msg = data_in.readUTF(); //input
                            //System.out.println(in_msg);
                        }
                    }
                    else if (in_msg.equals("joystick")) {
                        in_msg = data_in.readUTF();//input 
                        while (! in_msg.equals("exit")){
                            if(in_msg.equals("left_click")){
                                robo.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                robo.delay(10);
                                robo.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            }
                            else if(in_msg.equals("right_click")){
                                robo.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                                robo.delay(10);
                                robo.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                            }
                            else if(in_msg.equals("enter")){
                                robo.keyPress(KeyEvent.VK_ENTER);
                                robo.delay(10);
                                robo.keyRelease(KeyEvent.VK_ENTER);
                            }
                            else if(in_msg.equals("space")){
                                robo.keyPress(KeyEvent.VK_SPACE);
                                robo.delay(10);
                                robo.keyRelease(KeyEvent.VK_SPACE);
                            }
                            else if(in_msg.equals("esc")){
                                robo.keyPress(KeyEvent.VK_ESCAPE);
                                robo.delay(10);
                                robo.keyRelease(KeyEvent.VK_ESCAPE);
                            }
                            else keyStroke(in_msg.charAt(0));
                            in_msg = data_in.readUTF();//input
                            System.out.println(in_msg);
                        }
                        
                    }
                    else if (in_msg.equals("keyboard")){
                        in_msg = data_in.readUTF();//input 
                        while (! in_msg.equals("exit")){
                            if(in_msg.equals("enter")){
                                robo.keyPress(KeyEvent.VK_ENTER);
                                robo.delay(10);
                                robo.keyRelease(KeyEvent.VK_ENTER);
                            }
                            else if(in_msg.equals("space")){
                                robo.keyPress(KeyEvent.VK_SPACE);
                                robo.delay(10);
                                robo.keyRelease(KeyEvent.VK_SPACE);
                            }
                            else if(in_msg.equals("esc")){
                                robo.keyPress(KeyEvent.VK_ESCAPE);
                                robo.delay(10);
                                robo.keyRelease(KeyEvent.VK_ESCAPE);
                            }
                            else if(in_msg.equals("backspace")){
                                robo.keyPress(KeyEvent.VK_BACK_SPACE);
                                robo.delay(10);
                                robo.keyRelease(KeyEvent.VK_BACK_SPACE);
                            }
                            else keyStroke(in_msg.charAt(0));
                            in_msg = data_in.readUTF();//input
                            System.out.println(in_msg);
                        }
                        
                    }
                    else if (in_msg.equals("filetransfer")){
                        //FileOutputStream data_out = new FileOutputStream(sock.getOutputStream());
                        
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
                    else if (in_msg.equals("filerecieve")){
                        byte[] data;
                        String filename;
                        int fileSize;

                        fileSize = data_in.readInt();
                        filename = data_in.readUTF();
                        data = new byte[fileSize];
                        data_in.readFully(data,0,fileSize);

                        String path = "D://PC Controller//";
                        File dir = new File(path);
                        if(!dir.exists()) dir.mkdir();
                        path = path + filename;
                        System.out.println(path);
                        File outfile = new File(path);
                        FileOutputStream out_stream = new FileOutputStream(outfile);
                        out_stream.write(data,0,fileSize);
                        System.out.println("file saved");
                        out_stream.close();
                    }
                    else if (in_msg.equals("touch")) {
                        // 
                        String check = "";
                        while(! check.equals("exit")) {
                            ByteArrayOutputStream out_stream = new ByteArrayOutputStream();
                            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                            BufferedImage buf = robo.createScreenCapture(screenRect);
                            ImageIO.write(buf,"jpg",out_stream);
                            byte[] imageArr = out_stream.toByteArray();
                            out_stream.flush();
                            data_out.writeInt(imageArr.length); //output
                            data_out.flush();
                            data_in.readUTF();
                            data_out.write(imageArr); //output
                            data_out.flush();
                            System.out.print("sent :");
                            System.out.println(imageArr.length);
                            check = data_in.readUTF();
                            Thread.sleep(5000);
                        }  
                    }
                    else if (in_msg.equals("screenshot")){ 
                        Rectangle screenRect;
                        BufferedImage buf;
                        byte[] imageArr;
                        ByteArrayOutputStream out_stream = new ByteArrayOutputStream();
                        //
                        screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                        buf = robo.createScreenCapture(screenRect);
                        ImageIO.write(buf,"jpg",out_stream);
                        imageArr = out_stream.toByteArray();
                        //
                        data_out.writeInt(imageArr.length); //output
                        data_out.flush();
                        data_out.write(imageArr); //output
                        data_out.flush();
                    }
                    //System.out.println("back");
                    in_msg = data_in.readUTF();//input
                    System.out.println(in_msg);
                }
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==wifi){
            System.out.println("wifi selected");
            mode = true;
            try{wifi_remote();}
            catch(Exception exp){}
        }
        else if(e.getSource() == bluetooth){
            mode = false;
            System.out.println("bluetooth selected");
            try {bluetooth_remote();}
            catch(Exception exp) {}
        }
    }
    public static void main (String args[]){
    	server2 serv = new server2();
    	f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        p.setLayout(null);
        p.setBackground(Color.BLUE);
        f.setSize(600, 400);
        f.setSize(600, 400);
        f.setLocation(200,100);

        JLabel text = new JLabel();
        text.setText("SELECT A CONNECTION MODE");
        text.setFont(text.getFont().deriveFont(20.0f));
        address.setFont(text.getFont().deriveFont(20.0f));
        address2.setFont(text.getFont().deriveFont(20.0f));
        try{ip = InetAddress.getLocalHost();}
        catch(Exception e) {};
        address.setText(ip.getHostAddress());
        try{localDevice = LocalDevice.getLocalDevice();}
        catch(Exception e) {}
        address2.setText(localDevice.getBluetoothAddress());
        address.setVisible(false);
        address2.setVisible(false);
        p.add(bluetooth);
        p.add(wifi);
        p.add(text);
        p.add(address);
        text.setBounds(150,50,400,40);
        address.setBounds(250,150,100,40);
        address2.setBounds(250,150,100,40);
        bluetooth.setBounds(170,100,130,40);
        wifi.setBounds(300,100,130,40);
        f.add(p);
        f.setVisible(true);
        
    }    
}