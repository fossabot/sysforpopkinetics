// spkpvm.h

#ifndef SPKPVM_H
#define SPKPVM_H

#include <pvm3.h>

// function prototypes
extern const char *spkpvm_spawn_error(int);

// paths
#define SPK_SHARE      "/usr/local/spk/share"
//#define SPK_WORKING    "/usr/local/spk/share/working"
//#define SPKLOG_PATH    "/usr/local/spk/share/log/messages"

// message tags
#define SpkPvmExitValue       500
#define SpkPvmLogMessage      501
#define SpkPvmDataPackage     502
#define SpkPvmErrorMessage    503
#define SpkPvmResult          504
#define SpkPvmReturn          505

// special exit values
#define SpkPvmUnreported       -1
#define SpkPvmDied             -2

// arbitrary program limits
#define SPK_MAX_INDIVIDUALS 1000

#endif // SPKPVM_H
