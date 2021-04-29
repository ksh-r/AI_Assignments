function [reward] = bandit_nonstat(action)
m = [.1 .1 .1 .1 .1 .1 .1 .1 .1 .1]; % Mean Reawards, which can be adjusted according to need
reward = m(action)+ 0.01.*randn();
end