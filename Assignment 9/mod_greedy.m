val = zeros(1,10);
count = zeros(1,10);
Rn = zeros(1,10000);
Rn1 = zeros(1,10000);
itr = zeros(1,10000);
epsilon = 0.1;
sum = 0;
alpha = 0.2;
sum1 = 0; % used for taken average for those 1000 iterations.

% reward for each action is calculated for 1000 times
% then after computing reward again it is computed for 1000 to and taken average
% Therefore, total no. of iterations = 1000*1000= 1000000

for j = 1:10000
    Rn = zeros(1,10000);
    sum =0;
    for k = 1:10000
        itr(k)=k;
        if rand > epsilon
            [m,id]= max(val);
            A=id;
        else
            temp= randperm(10);
            A=temp(1);
        end
        R=bandit_nonstat(A); % Reward calculation based on mean reward and action taken
        count(A)=count(A)+1; % Count Update
        val(A)= val(A)+ (R-val(A))*alpha; % Value Update
        sum = sum + R;
        Rn(k)= sum/k;       
    end
    sum1 = sum1 + Rn(j);
    Rn1(j)=sum1/j;
end    
plot(itr,Rn1);
