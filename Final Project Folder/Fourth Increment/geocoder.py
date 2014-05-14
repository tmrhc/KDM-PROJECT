import pygeocoder 
f = open(r'C:\Users\Nandu\phython\data_out.txt') 
file_name2 = 'C:\Users\Nandu\phython\out_duplicate.txt' 
f2=open(file_name2,'w')
 for line in f: 
	city = '' "
	a = True
	 i = 8 
	x = line[i] 
	while a: 
		city += x
		 i += 1 
		x = line[i]
		 if (x == '/' )| (x=='"'): 
			a = False 
	r = Geocoder.geocode(city) 
	    y = line[-1:3]+",\"lat\"""+str(r[0].coordinates[0])+"\",\"long\"""+ 									str(r[0].coordinates[1])+"\"}]\n" f2.write(y)
