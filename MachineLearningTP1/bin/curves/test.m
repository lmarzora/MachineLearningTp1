function P = test(Y) 
	i = 1;
	while(1000+i<length(Y)-1) 
		T = Y(i:i+1000);
		P(i) = sum(T)/1000;
		i+=1000;
	
	endwhile
end



