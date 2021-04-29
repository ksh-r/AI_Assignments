val = zeros(1,10); % change 10 to n if there are n actions (eg. in case of binary bandit n=2) 
count = zeros(1,10); % change 10 to n if there are n actions (eg. in case of binary bandit n=2)
Rn= zeros(1,10000);
a =zeros(1,10000);
epsilon=0.1;
sum=0;

for i = 1:10000
    a(i)=i;
    if rand > epsilon
        [m,id]= max(val);
        A= id;
    else
        temp= randperm(10);
        A= temp(1);
    end
    R= bandit_nonstat(A); % Reward calculation based on mean reward and action taken
    count(A)= count(A)+1; % Count Update
    val(A)= val(A)+ alpha*(R-val(A)); % Value Update
    sum = sum + R;
    Rn(i)= sum/i; % Action state value estimate
end

plot(a,Rn); % Plot