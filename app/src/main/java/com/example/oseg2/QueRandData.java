package com.example.oseg2;

import static com.example.oseg2.DemoRenderer.randNum;

public class QueRandData {
    int que;int que_pos;int que_amnt_t1;int que_amnt_t2;int que_amnt_t3; int que_tick;

    void setRand(int tick){
        que_tick = tick;

        que = randNum(1, 3);
        que_pos = randNum(0, 15);
        que_amnt_t1 = randNum(que_pos + 3, que_pos + 12);
        que_amnt_t2 = randNum(que_pos + 2, que_pos + 5);
        que_amnt_t3 = randNum(que_pos + 1, 8);
    }
}
