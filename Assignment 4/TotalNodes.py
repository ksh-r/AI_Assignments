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

class TTT():
    def __init__(self):
        self.board=[
                ['_','_','_'],
                ['_','_','_'],
                ['_','_','_']
            ]
        self.count=1

    def dfs(self,isMax,depth):
        # base case
        if isWinner(self.board,isMax) is not None:
            return
        for i in range(3):
            for j in range(3):
                # Check if empty or not
                if self.board[i][j]=='_':
                    # "X" turn
                    if isMax:
                        self.board[i][j]='x'
                        self.count+=1
                        self.dfs(False,depth+1)
                        self.board[i][j]='_'
                    # "O" turn
                    else:
                        self.board[i][j]='o'
                        self.count+=1
                        self.dfs(True,depth+1)
                        self.board[i][j]='_'
    
    # calls the dfs function
    def helper(self):
        self.dfs(True,1)
        # print(self.count)
        return self.count




Tk=TTT()
x=Tk.helper()
print("Number of nodes in a tree : ",x)
