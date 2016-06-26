/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclockclienttwo;

import AlarmClock.Task;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlarmClockClientTwo {

    static Socket socket = new Socket();
    InetAddress ip;
    int port;
    static ArrayList<Task> list = new ArrayList<>();
    ObjectInputStream in;
    ObjectOutputStream out;
    String command;
    
    public static void main(String[] args) throws UnknownHostException, IOException {
        CTGUI.main(args);
        
        
        
    }
    
    public void go() throws UnknownHostException, IOException, ClassNotFoundException
    {
        port = CTGUI.getPort();
        ip = InetAddress.getByName(CTGUI.getIP());
        
        socket = new Socket(ip, port);
        System.out.println(socket);
        
        
        
        
    }
     
     
     
     
     
     public void viewList()
     {
         for (Task list1 : list) {
             System.out.println(list1.getDate());
             
             CTGUI.jTextArea1.append(list1.getName()+"\n");
             CTGUI.jTextArea1.append(list1.getDescription()+"\n");
             CTGUI.jTextArea1.append(list1.getDate().toString()+"\n");
             CTGUI.jTextArea1.append("-------------------------------- \n");
             CTGUI.jTextArea1.append("\n");
         }
     }

    void download() throws IOException, ClassNotFoundException 
    {
        in = new ObjectInputStream(socket.getInputStream());
       
        list = (ArrayList<Task>) in.readObject();
        
    }
    
    void commandSwitcher(String command)
    {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            
            switch (command)
            {
                case "download" :
                    out.writeObject(command);out.flush();
                    download();viewList();
                    break;
                case "END" : out.writeObject(command);
                    end();
                    break;
                case "upload" : out.writeObject(command);
                    upload();
                    break;
                case "delete" :
                    out.writeObject(command);
                    delete(CTGUI.jTextField9.getText());
                    break;
            }
            
            }
         catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(AlarmClockClientTwo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     void addList(Task task)
     {
         list.add(task);
     }

    private void upload() throws IOException 
    {
        
        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(list);
        out.flush();
    }

    private void end() throws IOException {
        in.close();out.close();
        socket.close();
    }

    private void delete(String deleteTaskName) throws IOException 
    {
        
        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(deleteTaskName);out.flush();
        
    }
}
