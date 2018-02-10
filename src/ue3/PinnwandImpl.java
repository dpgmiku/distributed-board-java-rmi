/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ue3;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

//HashMap logged 
/**
 *
 * @author admin
 */
public class PinnwandImpl extends UnicastRemoteObject implements Pinnwand {

    private static final long serialVersionUID = 1L;

    public static String nameOfService = "pinnwand";
    public static int maxNumMessages = 20;
    public static int messageLifetime = 10 * 60;  //in 10*60 s = 10 min
    public static int maxLengthMessage = 160;
    HashMap<String, Boolean> loggedClients = new HashMap<>();

    ArrayList<String> listOfMessages = new ArrayList<>();

    public PinnwandImpl() throws RemoteException {

        super();

    }

    @Override
    public String getMessage(int index) throws LoginFailedException, OutOfIndexException {
        if (areYouLogged()) {
            if (index < 0 || index >= getMessageCount()) throw new OutOfIndexException();
                return listOfMessages.get(index);
            
        
        
    }
        return null;
    }

    @Override
    public void login(String password) throws LoginFailedException {
        try {
            String clientHost = RemoteServer.getClientHost();
            loggedClients.put(clientHost, false);

            if ((password.equals("yolo"))) {
                loggedClients.put(clientHost, true);

                System.out.println("User " + clientHost + " logged in");

            } else {
                System.out.println("Password was invalid!");

                throw new LoginFailedException();
            }

        } catch (ServerNotActiveException ex) {
System.out.println(ex.toString());       
        }

    }

    @Override
    public int getMessageCount() throws LoginFailedException {

        if (areYouLogged()) {

            return listOfMessages.size();
        }

        return -1;

    }

    private Boolean areYouLogged() throws LoginFailedException {
        Boolean logged = false;

        try {
            logged = loggedClients.get(RemoteServer.getClientHost());
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(PinnwandImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (logged==null || logged==false) {

            throw new LoginFailedException();
        }
        return logged;

    }

    @Override
    public String[] getMessages() throws LoginFailedException {
        

        if (areYouLogged()) {
            
            if (! listOfMessages.isEmpty()) {
                String [] messages = new String[listOfMessages.size()];
                return listOfMessages.toArray(messages);
            } else return null;
        }

        return null;
    }

    @Override
    public void putMessage(String msg) throws MessageToLongException, TooManyMessagesException, LoginFailedException {
        if (areYouLogged()) {
            if (msg.length() <= 160) {
                if (getMessageCount() < 20) {
                    listOfMessages.add(msg);
                    new java.util.Timer().schedule(new java.util.TimerTask() {
                        @Override
                        public void run() {
                          listOfMessages.remove(msg);
                           
                         
                            
                            System.out.println("removed message " + msg);
                        }
                    },
                            messageLifetime * 10
                    );

                } else {
                    System.out.println("Maximale Anzahl der Nachrichten wurde überschritten");

                    throw new TooManyMessagesException();

                }
            } else {

                System.out.println("Your messages can not exceed 160 characters");
                throw new MessageToLongException();

            }

        }

    }

    public static void main(String args[]) {
        Registry registry;

        Pinnwand pinnwand;

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            if (args.length > 0) {
                registry = LocateRegistry.getRegistry(args[0]);
            } else {
                registry = LocateRegistry.getRegistry();
            }

            pinnwand = new PinnwandImpl();

            registry.bind(nameOfService, pinnwand);

            System.out.println("Pinnwand server started... get ready");

        } catch (RemoteException | AlreadyBoundException e) {
System.out.println(e.toString());        
        }
        // TODO Auto-generated catch block
        

    }

}
