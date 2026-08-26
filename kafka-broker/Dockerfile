FROM apache/kafka:3.7.0

USER root

# Tạo liên kết mềm /bin/bash trỏ đến /bin/sh vì ảnh nền tối giản của Apache Kafka không chứa bash
RUN ln -sf /bin/sh /bin/bash

RUN printf '#!/bin/sh\nexport KAFKA_ADVERTISED_LISTENERS="PLAINTEXT://${KAFKA_HOST}:9092"\nexec /opt/kafka/bin/launch "$@"\n' > /usr/local/bin/entrypoint.sh \
    && chmod +x /usr/local/bin/entrypoint.sh

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
