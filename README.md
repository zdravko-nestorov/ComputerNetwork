# ComputerNetwork

You analyze the performance of a computer network. The network comprises nodes connected by peer-to-peer links. There are N links and N+1 nodes. All pairs of nodes are (directly or indirectly) connected by links, and links don’t form cycles. In other words, the network has a tree topology.

Your analysis shows that communication between two nodes performs much better if the number of links on the (shortest) route between the nodes is odd. Of course, the communication is fastest when the two nodes are connected by a direct link. But, amazingly, if the nodes communicate via 3,5,7, etc. links, communication is much faster than if the number of links to pass is even.

Now you wonder how this influences the overall network performance. There are N * (N+1) / 2 different pairs of nodes. You need to compute how many of them are pairs of nodes connected via an odd number of links.

Nodes are numbered from 0 to N. Links are described by two arrays of integers, A and B, each containing N integers. For each 0 <= I < N, there is a link between nodes A[I] and B[I].

Write a function: Solution; that, gives two arrays, A and B, consisting of N integers and describing the links, computers, the number of pairs of nodex X and Y, such that 0<= X < Y <= N and X a d Y are connected via an odd number of links.

For example, given N = 6 and the following arrays:
A[0] = 0;
A[1] = 3;
A[2] = 4;
A[3] = 2;
A[4] = 6;
A[5] = 3;

B[0] = 3;
B[1] = 1;
B[2] = 3;
B[3] = 3;
B[4] = 3;
B[5] = 5;

The function should return 6, since:
•	There are six pairs of nodes connected by direct links
•	All other pairs of nodes are connected via two links

Given. N = 5 and the following arrays:
A[0] = 0;
A[1] = 4;
A[2] = 2;
A[3] = 2;
A[4] = 4;

B[0] = 1;
B[1] = 3;
B[2] = 1;
B[3] = 3;
B[4] = 5;

The function should return 9, since:
•	There are five pairs of nodes connected by direct links
•	There are three pairs of noes connected via three links
•	There is one pair of nodes connected via five links

Given N = 7 and the following arrays:
A[0] = 0;
A[1] = 4;
A[2] = 4;
A[3] = 2;
A[4] = 7;
A[5] = 6;
A[6] = 3;

B[0] = 3;
B[1] = 5;
B[2] = 1;
B[3] = 3;
B[4] = 4;
B[5] = 3;
B[6] = 4;

The function should return 16 since
•	There are seven pairs of noes connected by direct links
•	There are nine pairs of nodes connected via three links

Write an efficient algorithm for the following assumptions:
•	N is an integer within the range [0..90000];
•	Each element of arrays A, B is an integer within the range [0..N];
•	The network has a tree topology;
•	Any pair of noes is connected via no more than 1000 links
