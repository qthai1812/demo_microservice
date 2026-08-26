FROM apache/kafka:3.7.0

USER root

RUN printf '#!/bin/sh\nexport KAFKA_ADVERTISED_LISTENERS="PLAINTEXT://${KAFKA_HOST}:9092"\nexec /etc/kafka/docker/run "$@"\n' > /usr/local/bin/entrypoint.sh \
    && chmod +x /usr/local/bin/entrypoint.sh

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
