// Courtesy Alan Gasperini

#define _TIMER_H_


#include <windows.h>

class Timer
{
public:
	Timer();

	//in seconds
	float GetElapsedTime();
	void Reset();

private:
	LONGLONG cur_time;

	DWORD time_count;
	LONGLONG perf_cnt;
	bool perf_flag;
	LONGLONG last_time;
	float time_scale;

	bool QPC;
};

inline void Timer::Reset()
{
	last_time = cur_time;
}

