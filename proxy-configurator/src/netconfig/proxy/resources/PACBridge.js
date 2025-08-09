// Copyright © 2025 Devin B. Royal.
// All Rights Reserved.

const fs = require('fs');
const { promisify } = require('util');
const { default: createPacResolver } = require('pac-resolver');
const https = require('https');
const http = require('http');
const url = require('url');

function fetchPAC(pacUrl) {
  return new Promise((resolve, reject) => {
    const parsedUrl = url.parse(pacUrl);
    const protocol = parsedUrl.protocol === 'https:' ? https : http;
    protocol.get(pacUrl, res => {
      if (res.statusCode !== 200) {
        reject(new Error(`PAC fetch failed: ${res.statusCode}`));
        return;
      }
      let data = '';
      res.on('data', chunk => data += chunk);
      res.on('end', () => resolve(data));
    }).on('error', reject);
  });
}

async function main() {
  const [,, pacUrl, targetUrl] = process.argv;
  if (!pacUrl || !targetUrl) {
    console.error('Usage: node PACBridge.js <PAC_URL> <TARGET_URL>');
    process.exit(1);
  }

  try {
    const pacScript = await fetchPAC(pacUrl);
    const FindProxyForURL = await createPacResolver(pacScript);
    const proxy = await FindProxyForURL(targetUrl, new URL(targetUrl).hostname);
    console.log(proxy);
  } catch (err) {
    console.error('PAC evaluation failed:', err.message);
    process.exit(2);
  }
}

main();
