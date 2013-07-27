// Courtesy Alan Gasperini, my roommate

#include "timer.h"
#ifdef WIN32
#pragma comment(lib, "winmm.lib")
Timer::Timer()
{
	last_time=0;
	if(QueryPerformanceFrequency((LARGE_INTEGER*) &perf_cnt))
	{
		perf_flag=true;
		time_count=DWORD(perf_cnt); //perf_cnt counts per second
		QueryPerformanceCounter((LARGE_INTEGER*) &last_time);
		time_scale=1.0f/perf_cnt;
		QPC=true;
	}
	else
	{
		perf_flag=false;
		last_time=timeGetTime();
		time_scale=0.001f;
		time_count=33;
	}
}

float Timer::GetElapsedTime()
{
	if(perf_flag)
		QueryPerformanceCounter((LARGE_INTEGER*) &cur_time);
	else
		cur_time=timeGetTime();
				
	float time_elapsed=(cur_time-last_time)*time_scale;
	//last_time=cur_time;
	return time_elapsed;
}
#endif // unix