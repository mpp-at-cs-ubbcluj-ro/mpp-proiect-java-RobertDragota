package org.mpp2024;


import org.mpp2024.RcpProtocol.AppUserRpcReflectionWorker;

import java.net.Socket;


public class AppRpcConcurrentServer extends AbsConcurrentServer {
    private ServiceAppInterface chatServer;
    public AppRpcConcurrentServer(int port, ServiceAppInterface chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
       // ChatClientRpcWorker worker=new ChatClientRpcWorker(chatServer, client);
        AppUserRpcReflectionWorker worker=new AppUserRpcReflectionWorker(chatServer, client);

        return new Thread(worker);
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
