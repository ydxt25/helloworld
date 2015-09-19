#include <iostream>
#include <boost/asio.hpp>
using namespace std;

int main()
{
	boost::io_service _ios;
	_ios.run();	
	cout<<"helloworld!!!"<<endl;
	return 0;
}
