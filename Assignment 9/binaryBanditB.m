function [value] = binaryBanditB(action)

p = [.8 .9];
if rand < p(action)
	value = 1;
else
	value = 0;
end
end