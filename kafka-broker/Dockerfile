FROM apache/kafka:3.7.0

USER root

RUN echo '#!/bin/bash\n\
export KAFKA_ADVERTISED_LISTENERS="PLAINTEXT://${KAFKA_HOST}:9092"\n\
exec /opt/kafka/bin/launch "$@"' > /usr/local/bin/entrypoint.sh \
    && chmod +x /usr/local/bin/entrypoint.sh

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
