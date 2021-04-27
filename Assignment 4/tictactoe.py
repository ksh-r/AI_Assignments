from tkinter import *
import tkinter.messagebox

# Function to find about the state of game
def isWinner(board,chance):
    #used to check the winner in row
    for row in range(0,3):
        if board[row][0]==board[row][1]==board[row][2] and board[row][0] != '_':
            if chance:
                return -1
            else:
                return 1
    #used to check thw winner in column
    for col in range(0,3):
        if board[0][col]==board[1][col]==board[2][col] and board[0][col] != '_':
            if chance:
                return -1
            else:
                return 1
    #used to check winner in one diagonal
    if board[0][0]==board[1][1]==board[2][2] and board[0][0] !='_':
        if chance:
            return -1
        else:
            return 1
    #used to check winner in another diagonal
    if board[0][2]==board[1][1]==board[2][0] and board[0][2]!='_':
        if chance:
            return -1
        else:
            return 1
    
    for i in range(3):
        for j in range(3):
            if board[i][j]=='_':
                return None
    return 0


# AI finds the best move through minimax algorithm
# Here 'X' moves are auto generated through minimax
def bestMove(board,isNormal):
    best=float('-inf')
    move=[0,0]
    for i in range(3):
        for j in range(3):
            if board[i][j]=='_':
                board[i][j]='x'
                if isNormal:
                    # If normal minimax
                    score=minimax(board,False)
                else:
                    # if alpha beta pruning
                    score=minimax_alpha_beta_pruning(board,False,float('-inf'),float('inf'))
                # Backtracking
                board[i][j]='_'
                # Updates the score for best move
                if best<score:
                    best=score
                    move=[i,j]
    # Updates the board
    board[move[0]][move[1]]='x'
    return [move[0],move[1]]

# Keeps the count of number of times nodes visited in minimax
z1=0
def minimax(board,isMax):
    global z1
    # Weather game continues or not
    x=isWinner(board,isMax)
    if x is not None:
        return x
    z1+=1
    # If AI move
    if isMax:
        bestScore=float('-inf')
        for i in range(3):
            for j in range(3):
                if board[i][j]=='_':
                    board[i][j]='x'
                    # Recursive function call
                    p=minimax(board,False)
                    board[i][j]='_'
                    bestScore=max(bestScore,p)
        return bestScore
    # If player move
    else:
        bestScore=float('inf')
        for i in range(3):
            for j in range(3):
                if board[i][j]=='_':
                    board[i][j]='o'
                    # Recursive function call
                    p=minimax(board,True)
                    board[i][j]='_'
                    bestScore=min(bestScore,p)
        return bestScore

# Keeps the count of number of times Nodes visited in alpha-beta pruning
z2=0
def minimax_alpha_beta_pruning(board,isMax,a,b):
    global z2
    # Weather game continues or not
    x=isWinner(board,isMax)
    if x is not None:
        return x
    z2+=1
    # If AI move
    if isMax:
        bestScore=float('-inf')
        for i in range(3):
            for j in range(3):
                if board[i][j]=='_':
                    board[i][j]='x'
                    # Recursive function call
                    p=minimax_alpha_beta_pruning(board,False,a,b)
                    board[i][j]='_'
                    if p>a:
                        a=p
                    if a>=b:
                        break
        return a
    # If player move
    else:
        bestScore=float('inf')
        for i in range(3):
            for j in range(3):
                if board[i][j]=='_':
                    board[i][j]='o'
                    # Recursive function call
                    p=minimax_alpha_beta_pruning(board,True,a,b)
                    board[i][j]='_'
                    if p<b:
                        b=p
                    if a>=b:
                        break
        return b

# Prints the board
def printBoard(board):
    print("********************")
    for i in range(3):
        for j in range(3):
            print(board[i][j],end='  ')
        print()


# Main function
def play(isNormal):
    # 1 for AI first turn
    # 0 for player first turn
    flag=1
    while True:
        # If game not finished and player turn
        if isWinner(board,flag) is None and flag==0:
            [x,y]=list(map(int,input('O move : ').split(' ')))
            if board[x][y]!='_':
                print("Bad Move!")
                continue
            board[x][y]='o'
            flag=1
            printBoard(board)
        # If game not finished and AI turn
        elif isWinner(board,flag) is None and flag==1:
            a=bestMove(board,isNormal)
            flag=0
            print('X move : ',a[0],end=' ')
            print(a[1])
            printBoard(board)
        # If game finished and O is winner
        elif isWinner(board,flag)==-1:
            print('O is winner')
            print("*****************************")
            break
        # If game finished and X is winner
        elif isWinner(board,flag)==1:
            print('X is winner')
            print("*****************************")
            break
        # If game finished and it is a draw
        else:
            print('Draw!')
            print("*****************************")
            break
    if isNormal:
        ans=z1
        print("Minimax")
        print("Number of Visited Nodes : ",ans)
    else:
        ans=z2
        print("Minimax with alpha-beta pruning")
        print("Number of Visited Nodes : ",ans)
    



board=[
        ['_','_','_'],
        ['_','_','_'],
        ['_','_','_']
    ]

# print("Minimax with alpha-beta pruning")
play(False)
# print("Minimax")
board=[
        ['_','_','_'],
        ['_','_','_'],
        ['_','_','_']
    ]
play(True)
print("*****************************")

print("The difference between number of nodes between two approaches : ",(abs(z1-z2)))    



# print(z2)

# count=0
# dfs(board,True,1)
# print(count)
# play()






    















































# root=Tk()
# root.title("Tic Tac Toe")
# root.resizable(False,False)

# click=True
# count=0

# btn1=StringVar()
# btn2=StringVar()
# btn3=StringVar()
# btn4=StringVar()
# btn5=StringVar()
# btn6=StringVar()
# btn7=StringVar()
# btn8=StringVar()
# btn9=StringVar()

# xPhoto=PhotoImage(file='cross.png')
# oPhoto=PhotoImage(file='zero.png')

# def play():
#     button1=Button(root,height=9,width=19,relief='ridge',boderwidth=.5,background='#e6ffe6',
#             textvarianle=btn1)

# def press():
#     pass

# def checkWin():
#     pass

# def clear():
#     pass


# root.mainloop()
