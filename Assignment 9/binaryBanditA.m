function [value] = binaryBanditA(action)

p = [.1 .2];
if rand < p(action)
	value = 1;
else
	value = 0;
end
end