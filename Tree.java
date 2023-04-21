package application;

import java.util.*;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** 
 * 	The Tree class implements the Binary Search tree and its related methods as a data structure
 * 	The purpose of this method is to design a functional and balanced Red-Black Binary search tree
 * 	
 *  @author Christian Patterson, Reilly Downing
 * */
public class Tree {
    private Node root; // the root node of the tree

    /******************************* Implementation Here: *****************************************/
    
    public static final int BLACK = 0; // final variables created to avoid repetition of typing Node.RED/Node.BLACK
    public static final int RED = 1;
    
    // Helper variable for search function (changes to true if a value is already present in the tree)
    public static boolean found = false;
    
    /**
     * This method returns whether a givennode is red
     * 
     * @param node The node in question
     * @return True if node is Red, otherwise returns false
     */
    private boolean isRed(Node node) 
    {
        if (node == null) return false;	
        return (node.getColor() == RED);
    } // end isRed()
    
    /**
     * This method rotates nodes left around a given node
     * 
     * @param h the node around which rotation will occur
     * @return the right child of the given node, in its new location
     */
    private Node rotateLeft(Node h)
    {
        Node x = h.getRight();
        h.setRight(x.getLeft());
        
        x.setLeft(h);
        x.setColor(x.getLeft().getColor());
        x.getLeft().setColor(RED);
        
        return x;
    } // end rotateLeft()
    
    /**
     * This method rotates nodes right around a given node
     * 
     * @param h the node around which rotation will occur
     * @return the left child of the given node, in its new location
     */
    private Node rotateRight(Node h)
    {
        Node x = h.getLeft();
        h.setLeft(x.getRight());
        
        x.setRight(h);
        x.setColor(x.getRight().getColor());
        x.getLeft().setColor(BLACK);
        x.setColor(RED);
        
        return x;
    } // end rotateRight()
    
    /**
     * This method flips the colors of a given node and its children
     * 
     * @param x The chosen parent node
     * @return	the parent node
     */
    private Node colorFlip(Node x)
    {
        // swaps the color of node x
        if (x.getColor() == RED) {x.setColor(BLACK);}
        else {x.setColor(RED);}

        if (x.getLeft().getColor()==RED) {x.getLeft().setColor(BLACK);}
        else {x.getLeft().setColor(RED);}

        if (x.getRight().getColor()==RED) {x.getRight().setColor(BLACK);}
        else {x.getRight().setColor(RED);}

        return x;
    } //end colorFlip()
    
    /**
     * The main insertion method, this method calls the recursive insert function and sets the root to black. If the tree is empty, the node is set to the root.
     * 
     * @param node The desired node to be input
     * @return	True if the node is successfully inserted, returns False if a node with the given value exists in the tree
     */
    public boolean insertNode(Node node) 
    {
        found = false;

        if (root == null) {
            root = node;
            root.setColor(BLACK);
            return true;
        }

        root = insert(root, node);
        root.setColor(BLACK);

        return !found;
    } // end insertNode()
    
    /**
     * The recursive insertion method, the method recursively finds the proper location of the passed node and fixes the balanced criteria for the Tree
     * 
     * @param current The reference node for comparison, this tracks where the method is at in the tree
     * @param node The node to be inserted
     * @return the current node
     */
    private Node insert(Node current, Node node) 
    {
        if (current == null) {										// if the reference is null, insert the node here
            node.setColor(RED);
            return node;
        }
        
        int value = node.getValue();								// this if else/if chain compares the node's value with that of 'current' (the reference node)
        if (value < current.getValue()) {							//  and recursively calls the insert method to place the node left or right, in the proper location
            current.setLeft(insert(current.getLeft(), node));
        } else if (value > current.getValue()) {
            current.setRight(insert(current.getRight(), node));
            
        } else {													// if the node exists, no if statements will be run and the insertNode function will return false
            found = true;
        }
        
        
        																	// these if statements control the balanced properties of the tree,
        if (isRed(current.getRight()) && !isRed(current.getLeft() ) ) {		//  they handle when node should rotate left, rotate right, and flip colors
            current = rotateLeft(current);
        }
        
        if (isRed(current.getLeft()) && isRed(current.getLeft().getLeft())) {
            current = rotateRight(current);
        }
        
        if (isRed(current.getLeft()) && isRed(current.getRight())) {
            current = colorFlip(current);
        }
        
        return current;												// the method will recursively fix the tree as new nodes are added, maintaining balance at any stage
    } // end insert()
    
    
    
/*************************************************	End of Implementation   **************************************************************/

	
	
	
/***********************************************    Below are read-only! You don't need to make any changes ******************************/
/***********************************************	 The data members and functions below are just for the GUI Part ******************************************/
	
	/* Below are the GUI part you don't need to use*/
	private int tree_max_height; // record the maximum height of the tree
	private int tree_max_width; // record the maximum width of the tree
	private Canvas canvas; // the canvas where a node is drawn
	private GraphicsContext gc; // the brush used to draw on canvas
	private int canvas_width = 640;
	private int canvas_height = 480;
	private Vector<Vector<Node>> layer_nodes = new Vector<Vector<Node>>(); //store each node into the corresponding layer 
	private Node new_node; //the newly inserted node
	private double old_dragging_x;
	private double old_dragging_y;
	private boolean dragging = false;
	private Node selected_node;
	private double delta_x;
	private double delta_y;
	private int radius = 30; //the size of the node
	private int select_node_value; //indicate which node is selected

	
	

	
	public Tree() {

	}

	public Tree(Canvas c, GraphicsContext gc) {
		canvas = c;
		this.gc = gc;
	}
	
	
	
	/*
	 * The function below is for GUI use to make sure the tree fits in the
	 * canvas: (1) according to the current tree distribution, determine the
	 * tree height and width; (2) Check whether there is any overlap between
	 * tree nodes
	 */
	private void organizeTree() {
		//Reset several variables
		layer_nodes.clear(); //clear the layer
		
		
		//Set positions on Canvas
		
		
		
	}

        private void setNewProp(Node node){
            setNewNodeProperties(node);
            if(node.getLeft()!= null) setNewProp(node.getLeft());
            if(node.getRight()!= null) setNewProp(node.getRight());
        }
        
	// Draw the tree on canvas
	public void showTree(boolean insertion_occur) {
		
	
		if(insertion_occur)
		{
			//Set the basic properties for then new node
			setNewProp(root);
		}
		
		// Traverse the tree and draw all the nodes onto canvas
		bfsTreeDraw(this);
		

	}

	
	/*For mouse dragging use (update the sub-tree)*/
	private void updateTreePos(Node moving_node, double delta_x, double delta_y) {
		
		Queue<Node> queue = new LinkedList<Node>();
		Node current_node;
		
		queue.add(moving_node); // push the root node into queue
		while (!queue.isEmpty()) {
			current_node = queue.remove();
		
			// Check left child
			if (current_node.getLeft() != null) {
				Point2D pos = new Point2D(current_node.getLeft().getPosition().getX() + delta_x, current_node.getLeft().getPosition().getY() + delta_y);
				current_node.getLeft().setPosition(pos);
				queue.add(current_node.getLeft());
				
			}

			// Check right child
			if (current_node.getRight() != null) {
				Point2D pos = new Point2D(current_node.getRight().getPosition().getX() + delta_x, current_node.getRight().getPosition().getY() + delta_y);
				current_node.getRight().setPosition(pos);
				queue.add(current_node.getRight());
				
			}
		}

	}
	
	/* Apply Breath First Search Tree to render all the node */
	private void bfsTreeDraw(Tree tree) {
		Queue<Node> queue; // store the retrieved nodes from edges
		Node current_node; // point to the current node processing on

		if (tree.getRoot() == null) {
			return;
		}

		queue = new LinkedList<Node>();

		queue.add(tree.getRoot()); // push the root node into queue
		while (!queue.isEmpty()) {
			current_node = queue.remove();
			current_node.showNode(select_node_value); // draw the node on the canvas
		
			// Check left child
			if (current_node.getLeft() != null) {
				
				//Draw the edge between current node the the left child
				double start_x = current_node.getPosition().getX() + radius / 2;
				double start_y = current_node.getPosition().getY() + radius / 2;
				double end_x = current_node.getLeft().getPosition().getX() + radius / 2;
				double end_y = current_node.getLeft().getPosition().getY() + radius / 2;
				
				//Draw the edge
				gc.setStroke(Color.BLACK);
				gc.strokeLine(start_x, start_y, end_x, end_y);
				
				//Show current node again to cover the edge
				current_node.showNode(select_node_value); 
				
				queue.add(current_node.getLeft());
			}

			// Check right child
			if (current_node.getRight() != null) {
				//Draw the edge between current node the the right child
				double start_x = current_node.getPosition().getX() + radius / 2;
				double start_y = current_node.getPosition().getY() + radius / 2;
				double end_x = current_node.getRight().getPosition().getX() + radius / 2;
				double end_y = current_node.getRight().getPosition().getY() + radius / 2;
				
				//Draw the edge
				gc.setStroke(Color.BLACK);
				gc.strokeLine(start_x, start_y, end_x, end_y);
				
				//Show current node again to cover the edge
				current_node.showNode(select_node_value); 
				
				queue.add(current_node.getRight());
			}
		}

	}

	/* Apply Breath First Search Tree to (1) find the parent of the target_node and setup connection between them (2) set the GUI properties for the node*/
	private void setNewNodeProperties(Node target_node) {
	 
		Queue<Node> queue; // store the retrieved nodes from edges
		Node current_node; // point to the current node processing on
		layer_nodes.clear(); //clear all the layer
		
		if (target_node == root) {
		
			target_node.setDepth(0); //set the root depth to 0
			target_node.setParent(null);
			Point2D pos = new Point2D(320, 5); // do the setting for the root
			target_node.setPosition(pos);
			target_node.setDepth(0);
			
			
			Vector<Node> layer = new Vector<Node>();
			layer.add(target_node);
			layer_nodes.add(layer);
			target_node.setLayer_idx(0);
			
			return;
		}
		
	
		//Start the BFS search
		queue = new LinkedList<Node>();
		queue.add(getRoot()); // push the root node into queue	
	
		
		while (!queue.isEmpty()) {
			current_node = queue.remove();
			
			/* If current node is in a new layer, then create a new layer */
			if(layer_nodes.size() - 1 < current_node.getDepth())
			{
				Vector<Node> layer = new Vector<Node>();
				layer.add(current_node);
				layer_nodes.add(layer);
				current_node.setLayer_idx(0);
			}
			else
			{
				layer_nodes.get(current_node.getDepth()).add(current_node);
				current_node.setLayer_idx(layer_nodes.get(current_node.getDepth()).size() - 1);
			}
			
			if (current_node.getLeft() == target_node) {

				/*Set the depth and parent information*/
				target_node.setDepth(current_node.getDepth() + 1);
				target_node.setParent(current_node);
				target_node.setLeft_child_of_parent(true);	
			}
			else if(current_node.getRight() == target_node)
			{
				/*Set the depth and parent information*/
				target_node.setDepth(current_node.getDepth() + 1);
				target_node.setParent(current_node);
				target_node.setLeft_child_of_parent(false);
				
			}
			
			/*If the current node does not have the target node as one of its children, then keep finding it*/
			if (current_node.getLeft() != null)
				queue.add(current_node.getLeft());
			if (current_node.getRight() != null)
				queue.add(current_node.getRight());
			
		}
		

		/*Set the 2D position on Canvas*/
		setNodePosition(target_node);
	}
	
	/*Set the target_node position. This new node may affect the other node's current positions*/
	void setNodePosition(Node target_node)
	{
		double x_left_offset = -(double)radius * 6.0 / (double) target_node.getDepth() + 2.0; // the left child x relative position to the current node's x position (maximum offset)
		double x_right_offset = (double)radius * 6.0 / (double) target_node.getDepth() + 2.0; 
		int y_offset = radius * 2; // the left child x position relative (maximum offset)
		
		//Below are the two indicator whether need to adjust the tree size
		boolean layer_crowded = false;
		boolean tree_too_high = false;
		
	
		//Set position for the target_node
		Point2D pos = new Point2D(0,0); //the position will be assigned to the target_node
		if(target_node.isLeft_child_of_parent())
		{
			pos = new Point2D(target_node.getParent().getPosition().getX() + x_left_offset, target_node.getParent().getPosition().getY() + y_offset);
		}
		else
		{
			pos = new Point2D(target_node.getParent().getPosition().getX() + x_right_offset, target_node.getParent().getPosition().getY() + y_offset);
		}
		target_node.setPosition(pos);
		
		
		//Check this new node has overlapping with its left or right neighbors
		if(target_node.getLayer_idx() > 0) //check overlapping with left neighbor
		{
			int left_neighbor = target_node.getLayer_idx() - 1;
			Point2D left_pos = layer_nodes.get(target_node.getDepth()).get(left_neighbor).getPosition();
			if(pos.getX() - left_pos.getX() < radius)
			{
				layer_crowded = true;
				System.out.println("Overlapping with left! " + layer_nodes.get(target_node.getDepth()).get(left_neighbor).getValue());
			}
		}


		
	}
	
	
	/* Tracking the mouse event to see whether a node is being dragged. */
	public void finishNodeDragging(double x, double y)
	{
		dragging = false;
		delta_x = 0;
		delta_y = 0;
	}
	
	/* Tracking the mouse event to see whether a node is being dragged. */
	public void checkNodeDragging(double x, double y)
	{
		for(int j = 0; j < layer_nodes.size(); j++)
		{
			for(int i = 0; i < layer_nodes.get(j).size(); i++)
			{
				double node_x = layer_nodes.get(j).get(i).getPosition().getX();
				double node_y = layer_nodes.get(j).get(i).getPosition().getY();
				if(((node_x + radius/2 - x) * (node_x  + radius/2 - x) + (node_y + radius/2 - y) * (node_y  + radius/2- y)) < radius * radius / 4)
				{
					dragging = true;
					selected_node = layer_nodes.get(j).get(i);
					old_dragging_x = x;
					old_dragging_y = y;
					select_node_value = selected_node.getValue();

					gc.clearRect(0, 0, canvas_width, canvas_height);
					bfsTreeDraw(this);
					break;
				}
			}
			if(dragging == true)
				break;
		}
		
		//update the selection
		if(dragging == false)
		{
			select_node_value = -1;
			gc.clearRect(0, 0, canvas_width, canvas_height);
			bfsTreeDraw(this);
		}
		
	}
	
	/* If a node is being dragged, update its position and its children's positions */
	public void doNodeDragging(double x, double y)
	{
		// update dragged node position
		if (dragging == true) {

			delta_x = x - old_dragging_x;
			delta_y = y - old_dragging_y;
			Point2D pos = new Point2D(selected_node.getPosition().getX() + delta_x, selected_node.getPosition().getY() + delta_y);
			selected_node.setPosition(pos);
			old_dragging_x = x;
			old_dragging_y = y;
			updateTreePos(selected_node, delta_x, delta_y);
			gc.clearRect(0, 0, canvas_width, canvas_height);
			bfsTreeDraw(this);
		}

	}
	
	/* Adjust the tree width according to the layer value*/
	//layer_idx stores the layer index that has issue with the new node
	void adjustTreeWidth(int layer_idx)
	{
		int x_left_offset = -radius * 3; // the left child x relative position to the current node's x position (maximum offset)
		int x_right_offset = radius * 3;
		int y_offset = radius * 2; // the left child x position relative (maximum offset)
		
		//Calculate the width of problem layer = # * (node size + node gap) 
		int target_layer_width = layer_nodes.get(layer_idx).size() * radius * 2 + (layer_nodes.get(layer_idx).size() - 1) * x_right_offset * 2;
		Point2D layer_center = new Point2D(canvas_width/2, new_node.getPosition().getY()); 
		
		
		//If the problem layer width is smaller than the canvas width, then no need to adjust the node size but change the current layer's position
		if(target_layer_width < canvas_width)
		{
			//If there are even number of nodes
			if(layer_nodes.get(layer_idx).size() % 2 == 0)  
			{
				int left_node_besides_center = layer_nodes.get(layer_idx).size()/2;
				int right_node_besides_center =  layer_nodes.get(layer_idx).size()/2 + 1;
				System.out.println("center: " + layer_center.getX());
				//Update the nodes positions on the left side
				Point2D pos = new Point2D(layer_center.getX() + x_left_offset,  layer_center.getY());
				System.out.println("left: " + pos.getX());
				layer_nodes.get(layer_idx).get(left_node_besides_center).setPosition(pos);
				for(int i = left_node_besides_center - 1; i >= 0; i--)
				{
					pos = new Point2D(layer_nodes.get(layer_idx).get(i + 1).getPosition().getX() + x_left_offset * 2, layer_center.getY());
					layer_nodes.get(layer_idx).get(i).setPosition(pos);
				}
				
				//Update the nodes positions on the left side
				pos = new Point2D(layer_center.getX() + x_right_offset,  layer_center.getY());
				System.out.println("Right: " + pos.getX());
				layer_nodes.get(layer_idx).get(left_node_besides_center).setPosition(pos);
				for(int i = right_node_besides_center + 1; i < layer_nodes.get(layer_idx).size(); i++)
				{
					pos = new Point2D(layer_nodes.get(layer_idx).get(i - 1).getPosition().getX() + x_right_offset * 2, layer_center.getY());
					layer_nodes.get(layer_idx).get(i).setPosition(pos);
				}
				
				
			}
			//If there are odd number of nodes
			else
			{
				int node_at_center = (layer_nodes.get(layer_idx).size() + 1) /2;
				
				
				Point2D pos = new Point2D(layer_center.getX(),  layer_center.getY());
				layer_nodes.get(layer_idx).get(node_at_center).setPosition(pos);
				
				//Update the nodes positions on the left side
				for(int i = node_at_center - 1; i >= 0; i--)
				{
					pos = new Point2D(layer_nodes.get(layer_idx).get(i + 1).getPosition().getX() + x_left_offset * 2, layer_center.getY());
					layer_nodes.get(layer_idx).get(i).setPosition(pos);
				}
				
				//Update the nodes positions on the left side
				for(int i = node_at_center + 1; i < layer_nodes.get(layer_idx).size(); i++)
				{
					pos = new Point2D(layer_nodes.get(layer_idx).get(i - 1).getPosition().getX() + x_right_offset * 2, layer_center.getY());
					layer_nodes.get(layer_idx).get(i).setPosition(pos);
				}
			}
		}
		
	}

	/* Apply Breath First Search Tree to set basic tree properties for each node: (left or right) parent, children, depth, push node to corresponding layer */
	private void setBasicTreeProperties(Tree tree) {
		
		Queue<Node> queue; // store the retrieved nodes from edges
		Node current_node; // point to the current node processing on

		if (tree.getRoot() == null) {
			return;
		}

		tree.getRoot().setDepth(0); //set the root depth to 0
		tree.getRoot().setParent(null);
		
	
		//Start the BFS search
		queue = new LinkedList<Node>();
		queue.add(tree.getRoot()); // push the root node into queue
		while (!queue.isEmpty()) {
			current_node = queue.remove();
			
			/* If current node is in a new layer, then create a new layer */
			if(layer_nodes.size() - 1 < current_node.getDepth())
			{
				Vector<Node> layer = new Vector<Node>();
				layer.add(current_node);
				layer_nodes.add(layer);
			}
			else
			{
				layer_nodes.get(current_node.getDepth()).add(current_node);
			}
			

			// Check left child
			if (current_node.getLeft() != null) {
				current_node.getLeft().setParent(current_node); //let the left child point to the current node
				current_node.getLeft().setLeft_child_of_parent(true); //let the left child have left parent
				current_node.getLeft().setDepth(current_node.getDepth() + 1); //assign the depth to the left child
				
				queue.add(current_node.getLeft());
			}

			// Check right child
			if (current_node.getRight() != null) {
				current_node.getRight().setParent(current_node); //let the left child point to the current node
				current_node.getRight().setLeft_child_of_parent(false); //let the left child have left parent
				current_node.getRight().setDepth(current_node.getDepth() + 1); //assign the depth to the left child
				queue.add(current_node.getRight());
			}
		}

	}
	
	/* Because of the position change of one layer of nodes, this information is propagated to other layers */
	void changePropogation()
	{

		Node node = root; // node is just a temporary node holder

		Point2D pos = new Point2D(320, 0); // do the setting for the root
		root.setPosition(pos);
		
		
		int x_left_offset = -radius * 3; // the left child x relative 
													// position
													// to the current node's x position (maximum offset)
		int x_right_offset = radius * 3;
		int y_offset = radius * 2; // the left child x position relative (maximum offset)
		
		/* Preset all the nodes' positions; some of them maybe changed later*/
		for(int i = 1; i < layer_nodes.size(); i++)
		{
			for(int j = 0; j < layer_nodes.get(i).size(); j++)
			{
				if(layer_nodes.get(i).get(j).isLeft_child_of_parent())
					pos = new Point2D(layer_nodes.get(i).get(j).getParent().getPosition().getX() + x_left_offset, layer_nodes.get(i).get(j).getParent().getPosition().getY() + y_offset);
				else
					pos = new Point2D(layer_nodes.get(i).get(j).getParent().getPosition().getX() + x_right_offset, layer_nodes.get(i).get(j).getParent().getPosition().getY() + y_offset);
				
				layer_nodes.get(i).get(j).setPosition(pos);
			}
		}
		
		
		/*Traverse all the layers to see if any layer has node overlapping or out of the range issue */
		for(int y = layer_nodes.size() - 1; y >= 0; y--) //start from the bottom layer
		{

			boolean width_issue = false;  //an indicator that the tree suffers width problem 
			boolean height_issue = false; //an indicator the tree suffers height problem
			
			//Below is to process each layer
			for(int x = 0; x < layer_nodes.get(y).size(); x++)
			{
				//If the tree is too wide
				if(layer_nodes.get(y).get(x).getPosition().getX() < radius/2 || 
						layer_nodes.get(y).get(x).getPosition().getX() > (canvas_width - radius/2))
				{
					width_issue = true;
				}
				
				//If the tree is too high
				else if(layer_nodes.get(y).get(x).getPosition().getY() > (canvas_height - radius/2))
				{
					
				}
				
			}
		}
		
	}
	

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}


	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public Node getNew_node() {
		return new_node;
	}

	public void setNew_node(Node new_node) {
		this.new_node = new_node;
	}
	

	public int getSelect_node_value() {
		return select_node_value;
	}

	public void setSelect_node_value(int select_node_value) {
		this.select_node_value = select_node_value;
	}
	
	
	public Node getSelectedNode() {
		return selected_node;
	}

	public void setSelectedNode(Node dragged_node) {
		this.selected_node = dragged_node;
	}
}