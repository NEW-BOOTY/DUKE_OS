/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 */

 package net.sentient.mesh;

 import java.net.InetAddress;
 import java.util.HashSet;
 import java.util.Objects;
 import java.util.Set;
 
 /**
  * MeshNode represents an individual node within the EdgeMesh network.
  * Each node has a unique ID, address, and port, and can register peers for communication.
  */
 public class MeshNode {
 
     private final String nodeId;
     private final InetAddress address;
     private final int port;
     private final Set<MeshNode> peers = new HashSet<>();
 
     /**
      * Constructs a new MeshNode.
      *
      * @param nodeId   Unique identifier for the node
      * @param address  IP address of the node
      * @param port     Communication port
      */
     public MeshNode(String nodeId, InetAddress address, int port) {
         if (nodeId == null || nodeId.isEmpty()) {
             throw new IllegalArgumentException("Node ID cannot be null or empty.");
         }
         if (address == null) {
             throw new IllegalArgumentException("Address cannot be null.");
         }
         if (port <= 0 || port > 65535) {
             throw new IllegalArgumentException("Port must be between 1 and 65535.");
         }
 
         this.nodeId = nodeId;
         this.address = address;
         this.port = port;
     }
 
     public String getNodeId() {
         return nodeId;
     }
 
     public InetAddress getAddress() {
         return address;
     }
 
     public int getPort() {
         return port;
     }
 
     /**
      * Adds a peer node to the current node's peer list.
      *
      * @param peer Another MeshNode instance
      */
     public void addPeer(MeshNode peer) {
         if (peer == null) {
             throw new IllegalArgumentException("Peer cannot be null.");
         }
         if (!peer.equals(this)) {
             peers.add(peer);
         }
     }
 
     /**
      * Returns a snapshot of all known peer nodes.
      *
      * @return Immutable set of peer nodes
      */
     public Set<MeshNode> getPeers() {
         return Set.copyOf(peers);
     }
 
     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (!(o instanceof MeshNode)) return false;
         MeshNode node = (MeshNode) o;
         return port == node.port && nodeId.equals(node.nodeId);
     }
 
     @Override
     public int hashCode() {
         return Objects.hash(nodeId, port);
     }
 
     @Override
     public String toString() {
         return "MeshNode{" +
                 "nodeId='" + nodeId + '\'' +
                 ", address=" + address.getHostAddress() +
                 ", port=" + port +
                 '}';
     }
 }
 