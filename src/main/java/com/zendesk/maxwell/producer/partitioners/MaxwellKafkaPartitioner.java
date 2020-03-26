package com.zendesk.maxwell.producer.partitioners;

import com.zendesk.maxwell.row.RowMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaxwellKafkaPartitioner extends AbstractMaxwellPartitioner {
	static final Logger LOGGER = LoggerFactory.getLogger(MaxwellKafkaPartitioner.class);
	HashFunction hashFunc;

	public MaxwellKafkaPartitioner(String hashFunction, String partitionKey, String csvPartitionColumns, String partitionKeyFallback) {
		super(partitionKey, csvPartitionColumns, partitionKeyFallback);

		int MURMUR_HASH_SEED = 25342;
		switch (hashFunction) {
			case "murmur3": this.hashFunc = new HashFunctionMurmur3(MURMUR_HASH_SEED);
				break;
			case "default":
			default:
				this.hashFunc = new HashFunctionDefault();
				break;
		}
	}

	public int kafkaPartition(RowMap r, int numPartitions) {
		String hashString = this.getHashString(r);
		LOGGER.info("TTTTTTTTTTTTTTTTT-hashString:"+hashString);
		return Math.abs(hashFunc.hashCode(hashString) % numPartitions);
	}


}
